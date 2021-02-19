package com.mebr0
package transition.operator

case class Addition() extends BaseOperator {
  override def operate(first: Double, second: Double): Double = first + second
}
