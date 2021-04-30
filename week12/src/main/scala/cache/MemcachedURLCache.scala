package com.mebr0
package cache

import dto.URL

import org.slf4j.Logger
import shade.memcached.{Configuration, Memcached, MemcachedCodecs}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

class MemcachedURLCache(url: String)(implicit log: Logger, ec: ExecutionContext) extends URLCache with MemcachedCodecs {

  val cache: Memcached = Memcached(Configuration(url))

  override def put(url: URL): Future[URL] = {
    log.info("CACHE - PUT - {} with alias {}", url.original: Any, url.alias: Any)

    cache.set[URL](url.alias, url, 5.second)

    Future.successful(url)
  }

  override def get(alias: String): Future[Option[URL]] = {
    log.info("CACHE - GET - alias {}", alias)

    cache.get[URL](alias)
  }

  override def delete(alias: String): Future[Boolean] = {
    log.info("CACHE - DELETE - alias {}", alias)

    cache.delete(alias)
  }
}
