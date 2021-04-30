package com.mebr0

import cache.URLCache
import directive.{URLDirectives, ValidatorDirectives}
import dto.URLCreate
import repo.URLRepo
import validator.URLCreateValidator

import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.model.ContentTypes._
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentType, HttpCharsets, HttpHeader, HttpResponse, MediaTypes}
import akka.http.scaladsl.model.StatusCodes.Redirection
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.server.ContentNegotiator.Alternative.MediaType
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

trait RouterBase {
  def route: Route
}

class Router(repo: URLRepo, cache: URLCache)(implicit system: ActorSystem[_], context: ExecutionContext)
  extends RouterBase
    with Directives
    with URLDirectives
    with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  implicit val timeout: Timeout = 1.seconds

  override def route: Route = concat(
    pathPrefix("short") {
      path(Remaining) { alias =>
        get {
          handleWithGeneric(cache.get(alias)) {
            case Some(url) =>
              redirect(url.original, Redirection(307)("qwe", "qwe", "qwe"))
            case None =>
              handleWithGeneric(repo.get(alias)) {
                case Some(url) =>
                  cache.put(url)
                  redirect(url.original, Redirection(307)("qwe", "qwe", "qwe"))
                case None =>
                  complete(400, "<!DOCTYPE html>\n<html>\n<head>\n\t<title>Not found 404</title>\n</head>\n<body>\n\t<div id=\"main\">\n\t\t<div class=\"fof\">\n\t\t\t<h1>Error 404</h1>\n\t\t</div>\n\t</div>\n</body>\n</html>")
              }
          }
        }
      }
    },
    pathPrefix("urls") {
      concat(
        post {
          entity(as[URLCreate]) { url =>
            validateWith(URLCreateValidator)(url) {
              handleWithGeneric(repo.create(url)) {
                case Some(url) =>
                  cache.put(url)
                  complete(url)
                case None =>
                  complete(500, "cannot create url")
              }
            }
          }
        },
        path(Remaining) { alias =>
          pathEnd {
            concat(
              get {
                handleWithGeneric(cache.get(alias)) {
                  case Some(url) =>
                    complete(url)
                  case None =>
                    handleWithGeneric(repo.get(alias)) {
                      case Some(url) =>
                        cache.put(url)
                        complete(url)
                      case None =>
                        complete(400, "not found")
                    }
                }
              },
              delete {
                handleWithGeneric(repo.delete(alias)) {
                  case Some(url) =>
                    cache.delete(alias)
                    complete(url)
                  case None =>
                      complete(400, "not found")
                }
              }
            )
          }
        }
      )
    }
  )
}
