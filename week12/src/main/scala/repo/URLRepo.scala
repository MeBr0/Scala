package com.mebr0
package repo

import dto.{URL, URLCreate}

import scala.concurrent.Future

trait URLRepo {

  def create(url: URLCreate): Future[Option[URL]]

  def get(alias: String): Future[Option[URL]]

  def delete(alias: String): Future[Option[URL]]
}
