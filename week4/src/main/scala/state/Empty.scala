package com.mebr0
package state

import reader.Reader
import transition.operator.BaseOperator
import transition.{NonZero, Separator, Zero}


case class Empty(first: String, op: BaseOperator, second: String) extends BaseState {

  override def next(): BaseState = {
    println("Empty")
    Reader.read() match {
      case Zero() =>
        Empty(first, op, second)
      case NonZero(x: Int) =>
        AccumulateDigit(x.toString, op, second)
      case Separator() =>
        AccumulateDecimalDigit("0.", op, second)
      case _ =>
        Empty(first, op, second)
    }
  }

  override def isFinal: Boolean = false
}
