package com.mebr0
package transition.operator

/**
 * Base binary operator
 */
trait BaseOperator {

  /**
   * Do operation on two numbers
   */
  def operate(first: String, second: String): String
}
