package com.mebr0
package dto

case class Task(id: String, title: String, description: String, done: Boolean)

case class TaskCreate(title: String, description: String)
