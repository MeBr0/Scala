package com.mebr0
package state

import reader.Reader
import transition._

/**
 * State of accumulating first number
 */
case class AccumulateDigit(first: String) extends BaseState {

  override def isFinal: Boolean = false

  override def next(): BaseState = Reader.read() match {
    case Zero() =>
      AccumulateDigit(first + "0")
    case NonZero(digit) =>
      AccumulateDigit(first + digit)
    case Separator() =>
      AccumulateDecimal(first + ".")
    case Operator(operator) =>
      OperatorPending(first, operator)
    case _ =>
      AccumulateDigit(first)
  }
}
