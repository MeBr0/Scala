package com.mebr0
package repo

import dto.{Task, TaskCreate}

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class InMemoryTaskRepo(initialTasks: Seq[Task] = Seq.empty)(implicit ec: ExecutionContext) extends TaskRepo {

  private var tasks: Vector[Task] = initialTasks.toVector

  override def all(): Future[Seq[Task]] = Future.successful(tasks)

  override def create(taskCreate: TaskCreate): Future[Task] = {
    Future.successful {
      val task = Task(
        id = UUID.randomUUID().toString,
        title = taskCreate.title,
        description = taskCreate.description,
        done = false
      )
      tasks = tasks :+ task
      task
    }
  }

  override def exists(title: String): Future[Boolean] = Future.successful {
    tasks.map(_.title).contains(title)
  }
}
