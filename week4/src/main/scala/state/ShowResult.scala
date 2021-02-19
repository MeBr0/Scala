package com.mebr0
package state

import transition.operator.BaseOperator

case class ShowResult(first: String, op: BaseOperator, second: String) extends BaseState {

  override def next(): BaseState = {
    println(first, op, second)

    ShowResult(first, op, second)
  }

  override def isFinal: Boolean = true
}
