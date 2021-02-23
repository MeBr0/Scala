package com.mebr0
package state

import reader.Reader
import transition.{NonZero, Operator, Separator, Zero}

/**
 * State of accumulating first decimal number
 */
case class AccumulateDecimal(first: String) extends BaseState {

  override def isFinal: Boolean = false

  override def next(): BaseState = Reader.read() match {
    case Zero() =>
      AccumulateDecimal(first + "0")
    case NonZero(digit) =>
      AccumulateDecimal(first + digit)
    case Separator() =>
      AccumulateDecimal(first + ".")
    case Operator(operator) =>
      OperatorPending(first, operator)
    case _ =>
      AccumulateDecimal(first)
  }
}
