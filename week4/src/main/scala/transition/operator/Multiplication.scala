package com.mebr0
package transition.operator

/**
 * Binary multiplication operator
 */
case class Multiplication() extends BaseOperator {

  override def operate(first: String, second: String): String = (BigDecimal(first) * BigDecimal(second)).toString
}
