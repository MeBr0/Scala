package com.mebr0
package dto

case class URL(alias: String, original: String, createdAt: String, expiresAt: String)

case class URLCreate(original: String, alias: String = null, expiresAt: String)
