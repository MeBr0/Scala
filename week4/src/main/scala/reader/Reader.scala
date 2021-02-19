package com.mebr0
package reader

import transition._
import transition.operator.{Addition, Difference, Division, Multiplication}

import scala.io.StdIn

object Reader {

  val nonZero: List[Char] = List('1', '2', '3', '4', '5', '6', '7', '8', '9')
  val operations: List[Char] = List('+', '-', '*', '/')

  def read(): Transition = {
    val char = StdIn.readChar()

    if (char == '0') {
      Zero()
    }
    else if (nonZero.contains(char)) {
      NonZero(char.toInt - 48)
    }
    else if (operations.contains(char)) {
      char match {
        case '+' =>
          Operator(Addition.apply())
        case '-' =>
          Operator(Difference.apply())
        case '*' =>
          Operator(Multiplication.apply())
        case '/' =>
          Operator(Division.apply())
      }
    }
    else if (char == '.') {
      Separator()
    }
    else if (char == '=') {
      Equal()
    }
    else {
      Unknown()
    }
  }
}
