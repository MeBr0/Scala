package com.mebr0
package operator

/**
 * Binary division operator
 */
case class Division() extends BaseOperator {

  override def operate(first: String, second: String): String = (BigDecimal(first) / BigDecimal(second)).toString
}
