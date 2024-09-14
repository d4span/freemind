package org.freemind.tree

interface TreeNode<T> {
  val value: T

  val parent: TreeNode<T>?

  val childCount: Int

  val allowsChildren: Boolean

  val isLeaf: Boolean
    get() = this.childCount == 0

  operator fun get(index: Int): TreeNode<T>?

  operator fun get(child: TreeNode<T>): Int

  val children: java.util.List<out TreeNode<T>?>
}
