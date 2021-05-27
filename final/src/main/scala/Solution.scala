package com.mebr0

/**
 * Class with solutions Leetcode
 */
object Solution {

  /**
   * https://leetcode.com/problems/valid-anagram/
   */
  def isAnagram(s: String, t: String): Boolean = {
    count(s) == count(t)
  }

  /**
   * Count chars in string
   * @return Map with chars as keys and their count as values
   */
  def count(string: String): Map[Char, Int] = {
    // For value mapping with length after groupBy need to use view
    // And transform it again to Map
    string.groupBy(identity).view.mapValues(_.length).toMap
  }

  /**
   * https://leetcode.com/problems/increasing-order-search-tree/
   */
  def increasingBST(root: TreeNode): TreeNode = {
    // Create dummy (pre) node
    val dummy = new TreeNode()
    // And copy pointer for iterating
    var head = dummy

    /**
     * Traverse with DFS with in-order
     * For current value append new Node for right child of head
     * Then shift head to the right
     */
    def dfs(node: TreeNode): Unit = {
      if (node == null) {
        return
      }

      dfs(node.left)
      head.right = new TreeNode(node.value)
      head = head.right
      dfs(node.right)
    }

    // Start traverse from root and return right child of dummy node (actual first node)
    dfs(root)

    dummy.right
  }

  /**
   * https://leetcode.com/problems/maximum-depth-of-n-ary-tree/
   */
  def maxDepth(root: Node): Int = {
    // Deal with null node
    if (root == null)
      return 0

    // Deal with null children
    if (root.children == Nil)
      return 1

    // Otherwise add 1 for current layer and calculate depth of next layer
    1 + root.children.map(maxDepth).max
  }

  /**
   * https://leetcode.com/problems/maximum-depth-of-binary-tree/
   */
  def maxDepth(root: TreeNode): Int = {
    // Deal with null node
    if (root == null)
      return 0

    // Otherwise add 1 for current layer and calculate depth of next layer
    1 + Math.max(maxDepth(root.left), maxDepth(root.right))
  }

  /**
   * https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/
   */
  def canMakeArithmeticProgression(arr: Array[Int]): Boolean = {
    // Sort initial array
    val sorted = arr.sorted
    var result = true
    // Calculate difference for first pair
    val difference = sorted(1) - sorted(0)

    for (i <- 1 until sorted.length) {
      // Check other pairs for same difference
      if (sorted(i) - sorted(i - 1) != difference) {
        result = false
      }
    }

    result
  }

  /**
   * https://leetcode.com/problems/maximum-units-on-a-truck/
   */
  def maximumUnits(boxTypes: Array[Array[Int]], truckSize: Int): Int = {
    var result = 0
    var size = truckSize
    // Sort boxes by units (reversed)
    val sorted = boxTypes.sortWith((first, second) => first(1) > second(1))

    for (pair <- sorted) {
      val box = pair(0)
      val number = pair(1)

      if (size < box) {
        // Enough boxes
        result += size * number
        return result
      }
      else {
        // Reduce size
        size -= box
        result += box * number
      }
    }

    result
  }

  /**
   * https://leetcode.com/problems/intersection-of-two-arrays/
   */
  def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
    // Intersect arrays and choose distinct elements
    nums1.intersect(nums2).distinct
  }

  /**
   * https://leetcode.com/problems/largest-perimeter-triangle/
   */
  def largestPerimeter(nums: Array[Int]): Int = {
    nums.sortInPlace()

    for (i <- nums.length - 3 until -1 by -1) {
      // Check trio for triangle inequality
      if (nums(i + 2) < nums(i) + nums(i + 1))
        return nums(i) + nums(i + 1) + nums(i + 2)
    }

    0
  }
}
