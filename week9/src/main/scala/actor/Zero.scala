package com.mebr0
package actor

import actor.Calculator._

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

object Zero {

  sealed trait Command
  case class AppendZero(replyTo: ActorRef[BaseState], state: BaseState) extends Command

  def apply(): Behaviors.Receive[Command] = Behaviors.receiveMessage {
    case AppendZero(replyTo, state) =>
      state match {
        case AccumulateDigit(first) =>
          replyTo ! AccumulateDigit(first + "0")
          Behaviors.same
        case AccumulateDecimal(first) =>
          replyTo ! AccumulateDecimal(first + "0")
          Behaviors.same
        case AccumulateDigitSecond(first, op, second) =>
          replyTo ! AccumulateDigitSecond(first, op, second + "0")
          Behaviors.same
        case AccumulateDecimalSecond(first, op, second) =>
          replyTo ! AccumulateDecimalSecond(first, op, second + "0")
          Behaviors.same
        case _ =>
          Behaviors.same
      }
  }
}
