package com.mebr0

import actor.Calculator
import actor.Calculator.ShowResult
import dto.{CalcRequest, CalcResponse}
import reader.Reader

import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import io.circe.ObjectEncoder.importedObjectEncoder
import io.circe.generic.auto.exportEncoder
import io.circe.generic.decoding.DerivedDecoder.deriveDecoder

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

trait RouterBase {
  def route: Route
}

class Router(calculator: ActorRef[Calculator.Command])(implicit system: ActorSystem[_], context: ExecutionContext) extends RouterBase with Directives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  implicit val timeout: Timeout = 1.seconds

  override def route: Route = concat(
    path("calc") {
      post {
        entity(as[CalcRequest]) { req =>
          val res: Future[Calculator.BaseState] = calculator.ask(Reader.read(req.char, _))

          onSuccess(res) {
            case ShowResult(first, op, second) =>
              val r = CalcResponse(op.operate(first, second).toFloat)
              complete(StatusCodes.OK, r)
            case _ =>
              complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`text/plain(UTF-8)`, ""))
          }
        }
      }
    }
  )
}
