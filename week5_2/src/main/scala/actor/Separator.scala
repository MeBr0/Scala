package com.mebr0
package actor

import actor.Calculator._

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Separator {

  def apply(calculator: ActorRef[BaseState]): Behavior[BaseState] = Behaviors.receiveMessage {
    case AccumulateDigit(first) =>
      calculator ! AccumulateDecimal(first + ".")
      Behaviors.same
    case AccumulateDigitSecond(first, op, second) =>
      calculator ! AccumulateDecimalSecond(first, op, second + ".")
      Behaviors.same
    case Empty() =>
      calculator ! AccumulateDecimal("0.")
      Behaviors.same
    case OperatorPending(first, op) =>
      calculator ! AccumulateDecimalSecond(first, op, "0.")
      Behaviors.same
    case state =>
      calculator ! state
      Behaviors.same
  }
}
