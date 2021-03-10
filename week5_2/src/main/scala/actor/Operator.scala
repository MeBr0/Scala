package com.mebr0
package actor

import actor.Calculator._
import operator.BaseOperator

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Operator {

  case class Operate(state: BaseState, operator: BaseOperator)

  def apply(calculator: ActorRef[BaseState]): Behavior[Operate] = Behaviors.receiveMessage { message =>
    val operator = message.operator

    message.state match {
      case AccumulateDigit(first) =>
        calculator ! OperatorPending(first, operator)
        Behaviors.same
      case AccumulateDecimal(first) =>
        calculator ! OperatorPending(first, operator)
        Behaviors.same
      case AccumulateDigitSecond(first, op, second) =>
        calculator ! OperatorPending(op.operate(first, second), operator)
        Behaviors.same
      case AccumulateDecimalSecond(first, op, second) =>
        calculator ! OperatorPending(op.operate(first, second), operator)
        Behaviors.same
      case state =>
        calculator ! state
        Behaviors.same
    }
  }
}
