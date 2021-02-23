package com.mebr0

import state.{BaseState, Empty, ShowResult}

object Main {

  def main(args: Array[String]): Unit = {
    var state: BaseState = Empty()

    while (!state.isFinal)
      state = state.next()

    // Todo: handle errors
    state match {
      case ShowResult(first, op, second) =>
        println("Result is " + op.operate(first, second))
      case _ =>
        println("Error")
    }
  }
}
