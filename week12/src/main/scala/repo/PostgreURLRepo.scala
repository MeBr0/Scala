package com.mebr0
package repo
import dto.{URL, URLCreate}

import org.slf4j.Logger

import java.sql.DriverManager
import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class PostgreURLRepo(url: String)(implicit log: Logger, ec: ExecutionContext) extends URLRepo {

  Class.forName("org.postgresql.Driver")
  val connectionUrl: String = url

  override def create(url: URLCreate): Future[Option[URL]] = {
    log.info("REPO - CREATE - {0} with alias {1}", url.original: Any, url.alias: Any)

    val conn = DriverManager.getConnection(connectionUrl)
    val statement = conn.prepareCall("INSERT INTO urls (alias, original, created_at, expires_at) VALUES (?, ?, ?, ?)")

    statement.setString(1, url.alias)
    statement.setString(2, url.original)
    statement.setString(3, LocalDateTime.now().toString.substring(0, 20))
    if (url.expiresAt == null)
      statement.setString(4, LocalDateTime.now().plusMinutes(1).toString.substring(0, 20))
    else
      statement.setString(4, url.expiresAt)

    statement.execute()

    get(url.alias)
  }

  override def get(alias: String): Future[Option[URL]] =  {
    log.info("REPO - GET - alias {}", alias)

    val conn = DriverManager.getConnection(connectionUrl)
    val statement =
      conn.prepareCall("SELECT * FROM urls WHERE urls.alias = ?")

    statement.setString(1, alias)

    val res = statement.executeQuery()

    while (res.next()) {
      val alias = res.getString("alias")
      val original = res.getString("original")
      val createdAt = res.getString("created_at")
      val expiresAt = res.getString("expires_at")

      return Future.successful(Some(URL(alias, original, createdAt, expiresAt)))
    }

    Future.successful(None)
  }

  override def delete(alias: String): Future[Option[URL]] = {
    log.info("REPO - DELETE - alias {}", alias)

    val url = get(alias)

    val conn = DriverManager.getConnection(connectionUrl)
    val statement =
      conn.prepareCall("DELETE FROM urls WHERE urls.alias = ?")

    statement.setString(1, alias)

    statement.execute()

    url
  }
}
