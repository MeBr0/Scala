package com.mebr0
package actor

import actor.Calculator.{AccumulateDecimalSecond, AccumulateDigitSecond, BaseState, ShowResult}

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

object Equal {

  sealed trait Command
  case class Equate(replyTo: ActorRef[BaseState], state: BaseState) extends Command

  def apply(): Behaviors.Receive[Command] = Behaviors.receiveMessage {
    case Equate(replyTo, state) =>
      state match {
        case AccumulateDigitSecond(first, op, second) =>
          replyTo ! ShowResult(first, op, second)
          Behaviors.same
        case AccumulateDecimalSecond(first, op, second) =>
          replyTo ! ShowResult(first, op, second)
          Behaviors.same
        case _ =>
          Behaviors.same
      }
  }
}
