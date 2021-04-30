package com.mebr0
package cache

import dto.URL

import scala.concurrent.Future

trait URLCache {

  def put(url: URL): Future[URL]

  def get(alias: String): Future[Option[URL]]

  def delete(alias: String): Future[Boolean]
}
