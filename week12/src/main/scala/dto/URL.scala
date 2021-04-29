package com.mebr0
package dto

import akka.http.scaladsl.model.DateTime

case class URL(hash: String, original: String, createdAt: DateTime, expiresAt: DateTime)
