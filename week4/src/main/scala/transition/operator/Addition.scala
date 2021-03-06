package com.mebr0
package transition.operator

/**
 * Binary addition operator
 */
case class Addition() extends BaseOperator {

  override def operate(first: String, second: String): String = (BigDecimal(first) + BigDecimal(second)).toString
}
