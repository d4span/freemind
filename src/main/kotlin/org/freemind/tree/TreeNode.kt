package org.freemind.tree

interface TreeNode<T> {
    fun get(index: Int): TreeNode<T>?

    val childCount: Int

    val parent: TreeNode<T>?

    fun getIndex(child: TreeNode<T>): Int

    val allowsChildren: Boolean

    val isLeaf: Boolean

    val children: java.util.List<out TreeNode<T>?>

    val value: T
}
