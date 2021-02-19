package com.mebr0
package state

import reader.Reader
import transition.operator.BaseOperator
import transition.{NonZero, Zero}

case class OperatorPending(first: String, op: BaseOperator, second: String) extends BaseState {

  override def next(): BaseState = {
    println("OperatorPending")

    Reader.read() match {
      case Zero() =>
        AccumulateDigit(first, op, second)
      case NonZero(digit) =>
        AccumulateDigit(first, op, digit.toString)
      case _ =>
        OperatorPending(first, op, second)
    }
  }

  override def isFinal: Boolean = false
}
