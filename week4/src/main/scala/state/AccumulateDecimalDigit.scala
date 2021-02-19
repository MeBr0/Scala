package com.mebr0
package state

import transition.operator.BaseOperator

case class AccumulateDecimalDigit(first: String, op: BaseOperator, second: String) extends BaseState {
  override def next(): BaseState = ???

  override def isFinal: Boolean = false
}
