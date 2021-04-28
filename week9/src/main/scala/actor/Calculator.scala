package com.mebr0
package actor

import operator.BaseOperator

import akka.actor.typed
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior, Scheduler}
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext}

object Calculator {

  case class AccumulateDecimal(first: String) extends BaseState
  case class AccumulateDecimalSecond(first: String, op: BaseOperator, second: String) extends BaseState
  case class AccumulateDigit(first: String) extends BaseState
  case class AccumulateDigitSecond(first: String, op: BaseOperator, second: String) extends BaseState
  case class Empty() extends BaseState
  case class OperatorPending(first: String, op: BaseOperator) extends BaseState
  case class ShowResult(first: String, op: BaseOperator, second: String) extends BaseState

  trait BaseState extends Command

  case class EqualTransition(replyTo: ActorRef[BaseState]) extends Transition
  case class NonZeroTransition(replyTo: ActorRef[BaseState], digit: Int) extends Transition
  case class OperatorTransition(replyTo: ActorRef[BaseState], operator: BaseOperator) extends Transition
  case class SeparatorTransition(replyTo: ActorRef[BaseState]) extends Transition
  case class UnknownTransition(replyTo: ActorRef[BaseState]) extends Transition
  case class ZeroTransition(replyTo: ActorRef[BaseState]) extends Transition

  trait Transition extends Command

  case class AppendZero(replyTo: ActorRef[BaseState], state: BaseState) extends Command

  trait Command

  def apply(): Behavior[Command] = Behaviors.setup { context =>
    val zero = context.spawn(Zero(), "zero")
    val nonZero = context.spawn(NonZero(), "nonZero")
    val operator = context.spawn(Operator(), "operator")
    val separator = context.spawn(Separator(), "separator")
    val equal = context.spawn(Equal(), "equal")

    implicit val timeout: Timeout = 3.seconds
    implicit val executionContext: ExecutionContext = context.system.executionContext
    implicit val system: typed.ActorSystem[_] = context.system
    implicit val scheduler: Scheduler = system.scheduler

    def calculator(state: BaseState): Behaviors.Receive[Command] = Behaviors.receiveMessage {
      case ZeroTransition(replyTo) =>
        val newState: BaseState = Await.result(zero ? (ref => Zero.AppendZero(ref, state)), timeout.duration)
        replyTo ! newState
        calculator(newState)
      case NonZeroTransition(replyTo, digit) =>
        val newState: BaseState = Await.result(nonZero ? (ref => NonZero.AppendDigit(ref, state, digit)), timeout.duration)
        replyTo ! newState
        calculator(newState)
      case OperatorTransition(replyTo, op) =>
        val newState: BaseState = Await.result(operator ? (ref => Operator.Operate(ref, state, op)), timeout.duration)
        replyTo ! newState
        calculator(newState)
      case SeparatorTransition(replyTo) =>
        val newState: BaseState = Await.result(separator ? (ref => Separator.Separate(ref, state)), timeout.duration)
        replyTo ! newState
        calculator(newState)
      case EqualTransition(replyTo) =>
        val newState: BaseState = Await.result(equal ? (ref => Equal.Equate(ref, state)), timeout.duration)
        replyTo ! newState
        calculator(newState)
      case ShowResult(first: String, op: BaseOperator, second: String) =>
        Behaviors.same
      case _ =>
        Behaviors.same
    }

    calculator(Empty())
  }
}
