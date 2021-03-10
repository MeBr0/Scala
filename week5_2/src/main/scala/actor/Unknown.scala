package com.mebr0
package actor

import actor.Calculator.BaseState

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Unknown {

  def apply(calculator: ActorRef[BaseState]): Behavior[BaseState] = Behaviors.receiveMessage {
    state =>
      calculator ! state
      Behaviors.same
  }
}
