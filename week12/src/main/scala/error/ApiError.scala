package com.mebr0
package error

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {

  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")
  val emptyOriginalField: ApiError = new ApiError(StatusCodes.BadRequest, message = "Empty original.")
//  val emptyDescriptionField: ApiError = new ApiError(StatusCodes.BadRequest, message = "Empty description.")
//  val duplicateTitleField: ApiError = new ApiError(StatusCodes.BadRequest, message = "Duplicate title.")
}
