package com.mebr0
package reader

import actor.Calculator._
import operator.{Addition, Division, Multiplication, Substitution}

import akka.actor.typed.ActorRef

/**
 * Object for reading char and return appropriate Transition
 */
object Reader {

  val nonZero: List[Char] = List('1', '2', '3', '4', '5', '6', '7', '8', '9')
  val operations: List[Char] = List('+', '-', '*', '/')

  /**
   * Read next char and return appropriate Transition
   */
  def read(char: Char, replyTo: ActorRef[BaseState]): Transition = {
    if (char == '0') {
      ZeroTransition(replyTo)
    }
    else if (nonZero.contains(char)) {
      NonZeroTransition(replyTo, char.toInt - 48)  // ASCII shift
    }
    else if (operations.contains(char)) { // Binary operations
      char match {
        case '+' =>
          OperatorTransition(replyTo, Addition.apply())
        case '-' =>
          OperatorTransition(replyTo, Substitution.apply())
        case '*' =>
          OperatorTransition(replyTo, Multiplication.apply())
        case '/' =>
          OperatorTransition(replyTo, Division.apply())
      }
    }
    else if (char == '.') {
      SeparatorTransition(replyTo)
    }
    else if (char == '=') {
      EqualTransition(replyTo)
    }
    else {
      UnknownTransition(replyTo)
    }
  }
}
