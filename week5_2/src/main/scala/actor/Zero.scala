package com.mebr0
package actor

import actor.Calculator._

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Zero {

  def apply(calculator: ActorRef[BaseState]): Behavior[BaseState] = Behaviors.receiveMessage {
    case AccumulateDigit(first) =>
      calculator ! AccumulateDigit(first + "0")
      Behaviors.same
    case AccumulateDecimal(first) =>
      calculator ! AccumulateDecimal(first + "0")
      Behaviors.same
    case AccumulateDigitSecond(first, op, second) =>
      calculator ! AccumulateDigitSecond(first, op, second + "0")
      Behaviors.same
    case AccumulateDecimalSecond(first, op, second) =>
      calculator ! AccumulateDecimalSecond(first, op, second + "0")
      Behaviors.same
    case state =>
      calculator ! state
      Behaviors.same
  }
}
