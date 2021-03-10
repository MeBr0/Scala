package com.mebr0
package actor

import actor.Calculator.{AccumulateDecimal, AccumulateDecimalSecond, AccumulateDigit, AccumulateDigitSecond, BaseState, Empty, OperatorPending}

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object NonZero {

  case class AppendDigit(state: BaseState, digit: Int)

  def apply(calculator: ActorRef[BaseState]): Behavior[AppendDigit] = Behaviors.receiveMessage { message =>
    val digit = message.digit

    message.state match {
      case AccumulateDigit(first) =>
        calculator ! AccumulateDigit(first + digit)
        Behaviors.same
      case AccumulateDecimal(first) =>
        calculator ! AccumulateDecimal(first + digit)
        Behaviors.same
      case AccumulateDigitSecond(first, op, second) =>
        calculator ! AccumulateDigitSecond(first, op, second + digit)
        Behaviors.same
      case AccumulateDecimalSecond(first, op, second) =>
        calculator ! AccumulateDecimalSecond(first, op, second + digit)
        Behaviors.same
      case Empty() =>
        calculator ! AccumulateDigit(digit.toString)
        Behaviors.same
      case OperatorPending(first, op) =>
        calculator ! AccumulateDigitSecond(first, op, digit.toString)
        Behaviors.same
      case state =>
        calculator ! state
        Behaviors.same
    }
  }
}
