package com.mebr0
package state

import reader.Reader
import transition.{NonZero, Separator}

/**
 * Initial state without any stored data
 */
case class Empty() extends BaseState {

  override def isFinal: Boolean = false

  override def next(): BaseState = Reader.read() match {
    case NonZero(x: Int) =>
      AccumulateDigit(x.toString)
    case Separator() =>
      AccumulateDecimal("0.")
    case _ =>
      Empty()
  }
}
