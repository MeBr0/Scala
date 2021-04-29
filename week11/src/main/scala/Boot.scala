package com.mebr0

import repo.InMemoryTaskRepo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContextExecutor

object Boot extends App {

  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val rootBehavior = Behaviors.setup[Nothing] { context =>
    implicit val contextExecutor: ExecutionContextExecutor = context.executionContext
    implicit val system: ActorSystem[Nothing] = context.system

    val repo = new InMemoryTaskRepo()
    val router = new Router(repo)

    TaskServer.startHttpServer(router.route)
    Behaviors.empty
  }

  val system = ActorSystem[Nothing](rootBehavior, "CalculatorServer")
}
