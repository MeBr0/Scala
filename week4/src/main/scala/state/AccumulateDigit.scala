package com.mebr0
package state

import reader.Reader
import transition._
import transition.operator.BaseOperator

case class AccumulateDigit(first: String, op: BaseOperator, second: String) extends BaseState {

  override def next(): BaseState = {
    println("AccumulateDigit")

    Reader.read() match {
      case Zero() =>
        if (second == null)
          AccumulateDigit(first + "0", op, second)
        else
          AccumulateDigit(first, op, second + "0")
      case NonZero(digit) =>
        if (second == null)
          AccumulateDigit(first + digit, op, second)
        else
          AccumulateDigit(first, op, second + digit)
      case Separator() =>
        if (second == null)
          AccumulateDecimalDigit(first + ".", op, second)
        else
          AccumulateDecimalDigit(first, op, second + ".")
      case Operator(operator) =>
        if (second == null)
          OperatorPending(first, operator, second)
        else
          OperatorPending(op.operate(first.toDouble, second.toDouble).toString, null, null)
      case Equal() =>
        if (second == null)
          AccumulateDigit(first, op, second)
        else
          ShowResult(first, op, second)
      case _ =>
        AccumulateDigit(first, op, second)
    }
  }

  override def isFinal: Boolean = false
}
