package com.mebr0
package state

/**
 * State of FSM
 */
trait BaseState {

  /**
   * Whether BaseState in final or not
   */
  def isFinal: Boolean

  /**
   * Proceed to next BaseState
   */
  def next(): BaseState
}
