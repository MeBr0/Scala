package com.mebr0
package state

import transition.operator.BaseOperator

/**
 * Final state of showing result
 */
case class ShowResult(first: String, op: BaseOperator, second: String) extends BaseState {

  /**
   * Final state!
   */
  override def isFinal: Boolean = true

  override def next(): BaseState = ShowResult(first, op, second)
}
