package com.mebr0

import cache.MemcachedURLCache
import repo.PostgreURLRepo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContextExecutor

object Boot extends App {

  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val rootBehavior = Behaviors.setup[Nothing] { context =>
    implicit val contextExecutor: ExecutionContextExecutor = context.executionContext
    implicit val system: ActorSystem[Nothing] = context.system

    val repo = new PostgreURLRepo("jdbc:postgresql://localhost:5432/skala_url?currentSchema=skala_url&user=skala_url&password=qweqweqwe")
    val cache = new MemcachedURLCache("127.0.0.1:11211")
    val router = new Router(repo, cache)

    UrlServer.startHttpServer(router.route)
    Behaviors.empty
  }

  val system = ActorSystem[Nothing](rootBehavior, "TinyURL")
}
