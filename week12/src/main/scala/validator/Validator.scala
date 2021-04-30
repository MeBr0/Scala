package com.mebr0
package validator

import dto.URLCreate
import error.ApiError

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object URLCreateValidator extends Validator[URLCreate] {
  def validate(url: URLCreate): Option[ApiError] = {
    if (url.original.isEmpty)
      Some(ApiError.emptyOriginalField)
    else
      None
  }
}
