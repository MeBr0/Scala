package com.mebr0
package state

trait BaseState {

  def next(): BaseState

  def isFinal: Boolean
}
