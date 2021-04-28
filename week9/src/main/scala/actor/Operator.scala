package com.mebr0
package actor

import actor.Calculator._
import operator.BaseOperator

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors

object Operator {

  sealed trait Command
  case class Operate(replyTo: ActorRef[BaseState], state: BaseState, operator: BaseOperator) extends Command

  def apply(): Behaviors.Receive[Command] = Behaviors.receiveMessage {
    case Operate(replyTo, state, operator) =>
      state match {
        case AccumulateDigit(first) =>
          replyTo ! OperatorPending(first, operator)
          Behaviors.same
        case AccumulateDecimal(first) =>
          replyTo ! OperatorPending(first, operator)
          Behaviors.same
        case AccumulateDigitSecond(first, op, second) =>
          replyTo ! OperatorPending(op.operate(first, second), operator)
          Behaviors.same
        case AccumulateDecimalSecond(first, op, second) =>
          replyTo ! OperatorPending(op.operate(first, second), operator)
          Behaviors.same
        case _ =>
          Behaviors.same
      }
    }
}
