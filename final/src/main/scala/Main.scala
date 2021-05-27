package com.mebr0

object Main {
  def main(args: Array[String]): Unit = {
    Solution.isAnagram("qwe", "wqe")

    Solution.increasingBST(new TreeNode())

    Solution.maxDepth(new Node(1))

    Solution.maxDepth(new TreeNode())

    Solution.canMakeArithmeticProgression(Array(1, 2, 3))

    Solution.maximumUnits(Array(Array(1, 2), Array(2, 3)), 3)

    Solution.intersection(Array(2, 3, 5), Array(1, 2, 3))

    Solution.largestPerimeter(Array(2, 5, 3, 6, 7))
  }
}
