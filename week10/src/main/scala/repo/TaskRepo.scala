package com.mebr0
package repo

import dto.{Task, TaskCreate}

import scala.concurrent.Future

trait TaskRepo {

  def all(): Future[Seq[Task]]

  def create(taskCreate: TaskCreate): Future[Task]

  def exists(title: String): Future[Boolean]
}
