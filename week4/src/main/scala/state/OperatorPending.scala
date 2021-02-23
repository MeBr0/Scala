package com.mebr0
package state

import reader.Reader
import transition.operator.BaseOperator
import transition.{NonZero, Separator}

/**
 * State of defining operator
 */
case class OperatorPending(first: String, op: BaseOperator) extends BaseState {

  override def isFinal: Boolean = false

  override def next(): BaseState = Reader.read() match {
    case NonZero(digit) =>
      AccumulateDigitSecond(first, op, digit.toString)
    case Separator() =>
      AccumulateDecimalSecond(first, op, "0.")
    case _ =>
      OperatorPending(first, op)
  }
}
