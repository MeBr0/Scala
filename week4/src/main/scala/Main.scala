package com.mebr0

import state.{BaseState, Empty, ShowResult}

object Main {
  def main(args: Array[String]): Unit = {
    var state: BaseState = Empty(null, null, null)

    while (!state.isFinal) {
      state = state.next()
    }

    state match {
      case ShowResult(first, op, second) =>
        println("Result is " + op.operate(first.toDouble, second.toDouble))
      case _ =>
        println("Error")
    }
  }
}
