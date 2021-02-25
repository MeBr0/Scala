package com.mebr0

object Solution {

  /**
   * https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/
   */
  def maxProduct(nums: Array[Int]): Int = {
    getMaxElements(nums, 2).reduce((a, b) => (a - 1) * (b - 1))
  }

  def getMaxElements(nums: Array[Int], count: Int): Array[Int] = {
    val sorted = nums.sorted

    sorted.slice(sorted.length - count, sorted.length)
  }

  /**
   * https://leetcode.com/problems/average-salary-excluding-the-minimum-and-maximum-salary/
   */
  def average(salary: Array[Int]): Double = {
    val list = sliceMinAndMax(salary)

    list.sum * 1.0 / list.length
  }

  def sliceMinAndMax(salary: Array[Int]): Array[Int] = {
    val sorted = salary.sorted

    sorted.slice(1, sorted.length - 1)
  }

  /**
   * https://leetcode.com/problems/day-of-the-week/
   */
  def dayOfTheWeek(day: Int, month: Int, year: Int): String = {
    val days = Array("Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday")

    days((getAbsoluteDay(day, month, year)))
  }

  def getAbsoluteDay(day: Int, month: Int, year: Int): Int = {
    var absoluteDay: Int = 0
    val months: Array[Int] = Array(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
    var currentYear = 1971

    while (year > currentYear) {
      absoluteDay += 365

      if (currentYear % 4 == 0 && currentYear % 100 != 0 || currentYear % 400 == 0)
        absoluteDay += 1

      currentYear += 1
    }

    absoluteDay += months(month - 1) + day

    if (month > 2 && (currentYear % 4  == 0 && currentYear % 100 != 0 || currentYear % 400 == 0))
      absoluteDay += 1

    absoluteDay % 7
  }

  /**
   * https://leetcode.com/problems/k-diff-pairs-in-an-array/
   */
  def findPairs(nums: Array[Int], k: Int): Int = {
    val counted = count(nums)

    counted.count(pair => k > 0 && counted.contains(pair._1 + k) || k == 0 && pair._2 > 1)
  }

  def count(nums: Array[Int]): Map[Int, Int] = {
    nums.groupBy(identity).view.mapValues(_.length).toMap
  }
}
