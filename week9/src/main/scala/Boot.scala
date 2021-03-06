package com.mebr0

import actor.Calculator

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContextExecutor

object Boot extends App {

  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val rootBehavior = Behaviors.setup[Nothing] { context =>
    implicit val contextExecutor: ExecutionContextExecutor = context.executionContext
    implicit val system: ActorSystem[Nothing] = context.system

    val calculator = context.spawn(Calculator(), "Calculator")
    val router = new Router(calculator)

    CalcServer.startHttpServer(router.route)
    Behaviors.empty
  }

  val system = ActorSystem[Nothing](rootBehavior, "CalculatorServer")
}
