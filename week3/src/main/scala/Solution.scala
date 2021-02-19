package com.mebr0

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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
    mat.zipWithIndex.map(row => (row._1.sum, row._2)).sortBy(row => (row._1, row._2)).map(_._1).slice(0, k)
  }

  /**
   * https://leetcode.com/problems/find-positive-integer-solution-for-a-given-equation/
   */
  def findSolution(customfunction: CustomFunction, z: Int): List[List[Int]] = {
    def search(value: Int, min: Int, max: Int): List[List[Int]] = {
      if (min > max) {
        return List.empty
      }

      val mid = (min + max) / 2
      val result = customfunction.f(mid, value)

      if (result == z) {
        List(List(mid, value))
      }
      else if (result > z) {
        search(value, min, mid - 1)
      }
      else {
        search(value, mid + 1, max)
      }
    }

    (1 to 1000).flatMap(value => search(value, 1, 1000)).toList
  }

  /**
   * https://leetcode.com/problems/intersection-of-two-arrays/
   */
  def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
    nums1.intersect(nums2).distinct
  }

  /**
   * https://leetcode.com/problems/build-an-array-with-stack-operations/
   */
  def buildArray(target: Array[Int], n: Int): List[String] = {
    val result: ListBuffer[String] = ListBuffer()
    var i = 0
    var j = 1

    while (j <= n && i < target.length) {
      result.addOne("Push")

      if (j != target(i)) {
        result.addOne("Pop")
      }
      else {
        i += 1
      }

      j += 1
    }

    result.toList
  }
}
