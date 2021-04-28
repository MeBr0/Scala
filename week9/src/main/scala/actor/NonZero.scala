package com.mebr0
package actor

import actor.Calculator._

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

object NonZero {

  sealed trait Command
  case class AppendDigit(replyTo: ActorRef[BaseState], state: BaseState, digit: Int) extends Command

  def apply(): Behaviors.Receive[Command] = Behaviors.receiveMessage {
    case AppendDigit(replyTo, state, digit) =>
      state match {
        case AccumulateDigit(first) =>
          replyTo ! AccumulateDigit(first + digit)
          Behaviors.same
        case AccumulateDecimal(first) =>
          replyTo ! AccumulateDecimal(first + digit)
          Behaviors.same
        case AccumulateDigitSecond(first, op, second) =>
          replyTo ! AccumulateDigitSecond(first, op, second + digit)
          Behaviors.same
        case AccumulateDecimalSecond(first, op, second) =>
          replyTo ! AccumulateDecimalSecond(first, op, second + digit)
          Behaviors.same
        case Empty() =>
          replyTo ! AccumulateDigit(digit.toString)
          Behaviors.same
        case OperatorPending(first, op) =>
          replyTo ! AccumulateDigitSecond(first, op, digit.toString)
          Behaviors.same
        case _ =>
          Behaviors.same
      }
  }
}
