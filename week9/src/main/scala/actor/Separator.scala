package com.mebr0
package actor

import actor.Calculator._

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

object Separator {

  sealed trait Command
  case class Separate(replyTo: ActorRef[BaseState], state: BaseState) extends Command

  def apply(): Behaviors.Receive[Command] = Behaviors.receiveMessage {
    case Separate(replyTo, state) =>
      state match {
        case AccumulateDigit(first) =>
          replyTo ! AccumulateDecimal(first + ".")
          Behaviors.same
        case AccumulateDigitSecond(first, op, second) =>
          replyTo ! AccumulateDecimalSecond(first, op, second + ".")
          Behaviors.same
        case Empty() =>
          replyTo ! AccumulateDecimal("0.")
          Behaviors.same
        case OperatorPending(first, op) =>
          replyTo ! AccumulateDecimalSecond(first, op, "0.")
          Behaviors.same
        case _ =>
          Behaviors.same
      }
  }
}
