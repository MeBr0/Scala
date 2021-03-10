package com.mebr0
package operator

/**
 * Binary substitution operator
 */
case class Substitution() extends BaseOperator {

  override def operate(first: String, second: String): String = (BigDecimal(first) - BigDecimal(second)).toString
}
