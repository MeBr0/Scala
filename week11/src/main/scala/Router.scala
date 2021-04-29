package com.mebr0

import directive.{TaskDirectives, ValidatorDirectives}
import dto.TaskCreate
import error.ApiError
import repo.TaskRepo
import validator.TaskCreateValidator

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import io.circe.ObjectEncoder.importedObjectEncoder

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

trait RouterBase {
  def route: Route
}

class Router(repo: TaskRepo)(implicit system: ActorSystem[_], context: ExecutionContext)
  extends RouterBase
    with Directives
    with TaskDirectives
    with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  implicit val timeout: Timeout = 1.seconds

  override def route: Route = concat(
    path("tasks") {
      pathEndOrSingleSlash {
        concat(
          get {
            handleWithGeneric(repo.all()) {
              complete(_)
            }
          },
          post {
            entity(as[TaskCreate]) { task =>
              validateWith(TaskCreateValidator)(task) {
                handleWithGeneric(repo.exists(task.title)) {
                  exists =>
                    if (exists) {
                      complete(ApiError.duplicateTitleField.statusCode, ApiError.duplicateTitleField.message)
                    } else {
                      handleWithGeneric(repo.create(task)) {
                        complete(_)
                      }
                    }
                }
              }
            }
          }
        )
      }
    }
  )
}
