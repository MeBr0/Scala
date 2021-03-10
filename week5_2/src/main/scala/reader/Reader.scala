package com.mebr0
package reader

import actor.Calculator._
import operator.{Addition, Division, Multiplication, Substitution}

import scala.io.StdIn

/**
 * Object for reading char and return appropriate Transition
 */
object Reader {

  val nonZero: List[Char] = List('1', '2', '3', '4', '5', '6', '7', '8', '9')
  val operations: List[Char] = List('+', '-', '*', '/')

  /**
   * Read next char and return appropriate Transition
   */
  def read(): Transition = {
    val char = StdIn.readChar()

    if (char == '0') {
      ZeroTransition()
    }
    else if (nonZero.contains(char)) {
      NonZeroTransition(char.toInt - 48)  // ASCII shift
    }
    else if (operations.contains(char)) { // Binary operations
      char match {
        case '+' =>
          OperatorTransition(Addition.apply())
        case '-' =>
          OperatorTransition(Substitution.apply())
        case '*' =>
          OperatorTransition(Multiplication.apply())
        case '/' =>
          OperatorTransition(Division.apply())
      }
    }
    else if (char == '.') {
      SeparatorTransition()
    }
    else if (char == '=') {
      EqualTransition()
    }
    else {
      UnknownTransition()
    }
  }
}
