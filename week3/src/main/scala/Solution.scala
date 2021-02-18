package com.mebr0

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Class with solutions Leetcode
 */
object Solution {

  /**
   * https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/
   */
  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {
    candies.map(candy => candy + extraCandies >= candies.max)
  }

  /**
   * https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/
   */
  def getDecimalValue(head: ListNode): Int = {
    var node = head
    var result = 0

    while (node != null) {
      result *= 2
      result += node.x

      node = node.next
    }

    result
  }

  /**
   * https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/
   */
  def smallerNumbersThanCurrent(nums: Array[Int]): Array[Int] = {
    nums.map(num => nums.count(p => num > p))
  }

  /**
   * https://leetcode.com/problems/n-repeated-element-in-size-2n-array/
   */
  def repeatedNTimes(A: Array[Int]): Int = {
    var once: Set[Int] = Set()

    for (num <- A) {
      if (once.contains(num)) {
        return num
      }

      once += num
    }

    0   // Deal with it?
  }

  /**
   * https://leetcode.com/problems/decompress-run-length-encoded-list/
   */
  def decompressRLElist(nums: Array[Int]): Array[Int] = {
    var result = new ArrayBuffer[Int]()

    for (i <- nums.indices by 2) {
      result ++= ArrayBuffer.fill(nums(i))(nums(i + 1))
    }

    result.toArray
  }

  /**
   * https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/
   */
  def sumZero(n: Int): Array[Int] = {
    if (n % 2 == 1) {
      Array.range(-n / 2, n / 2 + 1)
    }
    else {
      (((- n / 2) to 0) ++ (1 to n / 2)).toArray
    }
  }

  /**
   * https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/
   */
  def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = {
    // Todo: solve this
  }

  /**
   * https://leetcode.com/problems/find-positive-integer-solution-for-a-given-equation/
   */
  def findSolution(customfunction: CustomFunction, z: Int): List[List[Int]] = {
    val result: List[List[Int]] = List()

    
  }
}
