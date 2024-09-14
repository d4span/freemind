package org.freemind.tree

interface MutableTreeNode<T> : TreeNode<T> {
  fun insert(child: MutableTreeNode<T>?, index: Int)

  fun remove(index: Int)

  fun remove(child: MutableTreeNode<T>?)

  fun removeFromParent()

  override var parent: MutableTreeNode<T>?

  override var value: T
}
