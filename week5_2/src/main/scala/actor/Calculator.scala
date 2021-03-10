package com.mebr0
package actor

import actor.NonZero.AppendDigit
import actor.Operator.Operate
import operator.BaseOperator

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Calculator {

  case class AccumulateDecimal(first: String) extends BaseState
  case class AccumulateDecimalSecond(first: String, op: BaseOperator, second: String) extends BaseState
  case class AccumulateDigit(first: String) extends BaseState
  case class AccumulateDigitSecond(first: String, op: BaseOperator, second: String) extends BaseState
  case class Empty() extends BaseState
  case class OperatorPending(first: String, op: BaseOperator) extends BaseState
  case class ShowResult(first: String, op: BaseOperator, second: String) extends BaseState

  trait BaseState extends Command

  case class EqualTransition() extends Transition
  case class NonZeroTransition(digit: Int) extends Transition
  case class OperatorTransition(operator: BaseOperator) extends Transition
  case class SeparatorTransition() extends Transition
  case class UnknownTransition() extends Transition
  case class ZeroTransition() extends Transition

  trait Transition extends Command

  trait Command

  def apply(): Behavior[Command] = Behaviors.setup { context =>
    val zero = context.spawn(Zero(context.self), "zero")
    val nonZero = context.spawn(NonZero(context.self), "nonZero")
    val operator = context.spawn(Operator(context.self), "operator")
    val separator = context.spawn(Separator(context.self), "separator")
    val equal = context.spawn(Equal(context.self), "equal")
    val unknown = context.spawn(Unknown(context.self), "unknown")

    def calculator(state: BaseState): Behavior[Command] = Behaviors.receiveMessage {
      case ZeroTransition() =>
        zero ! state
        Behaviors.same
      case NonZeroTransition(digit) =>
        nonZero ! AppendDigit(state, digit)
        Behaviors.same
      case OperatorTransition(op) =>
        operator ! Operate(state, op)
        Behaviors.same
      case SeparatorTransition() =>
        separator ! state
        Behaviors.same
      case UnknownTransition() =>
        unknown ! state
        Behaviors.same
      case EqualTransition() =>
        equal ! state
        Behaviors.same
      case ShowResult(first: String, op: BaseOperator, second: String) =>
        println("Result is " + op.operate(first, second))
        Behaviors.stopped
      case newState: BaseState =>
        calculator(newState)
    }

    calculator(Empty())
  }
}
