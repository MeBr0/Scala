package com.mebr0
package actor

import actor.Calculator.{AccumulateDecimalSecond, AccumulateDigitSecond, BaseState, ShowResult}

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Equal {

  def apply(calculator: ActorRef[BaseState]): Behavior[BaseState] = Behaviors.receiveMessage {
    case AccumulateDigitSecond(first, op, second) =>
      calculator ! ShowResult(first, op, second)
      Behaviors.same
    case AccumulateDecimalSecond(first, op, second) =>
      calculator ! ShowResult(first, op, second)
      Behaviors.same
    case state =>
      calculator ! state
      Behaviors.same
  }
}
