package com.mebr0
package transition.operator

/**
 * Binary division operator
 */
case class Division() extends BaseOperator {

  override def operate(first: String, second: String): String = (BigDecimal(first) / BigDecimal(second)).toString
}
