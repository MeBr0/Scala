package com.mebr0

import actor.Calculator
import reader.Reader

import akka.actor.typed.ActorSystem

object Main extends App {

  val calculator = ActorSystem(Calculator(), "calculator")

  while (true) {
    calculator ! Reader.read()
  }
}
