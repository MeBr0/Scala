package com.mebr0
package state

import reader.Reader
import transition._
import transition.operator.BaseOperator

/**
 * State of accumulating second decimal number
 */
case class AccumulateDecimalSecond(first: String, op: BaseOperator, second: String) extends BaseState {

  override def isFinal: Boolean = false

  override def next(): BaseState = Reader.read() match {
    case Zero() =>
      AccumulateDecimalSecond(first, op, second + "0")
    case NonZero(digit) =>
      AccumulateDecimalSecond(first, op, second + digit)
    case Operator(operator) =>
      OperatorPending(op.operate(first, second), operator)
    case Equal() =>
      ShowResult(first, op, second)
    case _ =>
      AccumulateDigitSecond(first, op, second)
  }
}
