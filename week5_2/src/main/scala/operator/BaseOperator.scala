package com.mebr0
package operator

/**
 * Base binary operator
 */
trait BaseOperator {

  /**
   * Do operation on two numbers
   */
  def operate(first: String, second: String): String
}