package com.mebr0
package directive

import error.ApiError

import akka.http.scaladsl.server.{Directive1, Directives}

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait TaskDirectives extends Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  def handle[T](f: Future[T])(e: Throwable => ApiError): Directive1[T] = onComplete(f) flatMap {
    case Success(t) =>
      provide(t)
    case Failure(error) =>
      val apiError = e(error)
      return complete(apiError.statusCode, apiError.message)
  }

  def handleWithGeneric[T](f: Future[T]): Directive1[T] = handle[T](f)(_ => ApiError.generic)
}
