package com.mebr0
package validator

import dto.TaskCreate
import error.ApiError

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object TaskCreateValidator extends Validator[TaskCreate] {
  def validate(task: TaskCreate): Option[ApiError] = {
    if (task.title.isEmpty)
      Some(ApiError.emptyTitleField)
    else if (task.description.isEmpty)
      Some(ApiError.emptyDescriptionField)
    else
      None
  }
}
