package ch.d4span.freemind.mindmap

import javax.swing.event.TreeModelListener
import javax.swing.tree.TreePath

interface TreeNode<V> {
    val childCount: Int

    fun isLeaf(): Boolean {
        TODO("Not yet implemented")
    }

    fun getIndex(child: TreeNode<V>): Int {
        TODO("Not yet implemented")
    }

    fun getChildAt(index: Int): TreeNode<V> {
        TODO("Not yet implemented")
    }

    fun getUserObject(): V {
        TODO("Not yet implemented")
    }

    fun getParent(): TreeNode<V> {
        TODO("Not yet implemented")
    }
}

interface MutableTreeNode<V> : TreeNode<V> {
    fun remove(child: MutableTreeNode<V>) {
        TODO("Not yet implemented")
    }

    fun removeFromParent() {
        TODO("Not yet implemented")
    }

    fun insert(
        child: MutableTreeNode<V>,
        index: Int,
    ) {
        TODO("Not yet implemented")
    }

    fun setUserObject(userObject: V) {
        TODO("Not yet implemented")
    }

    fun setParent(node: MutableTreeNode<V>) {
        TODO("Not yet implemented")
    }
}

interface EventListenerList {
    val listenerList: Array<Any>
}

interface TreeModel<V> {
    val root: TreeNode<V>

    fun removeNodeFromParent(node: TreeNode<V>) {
        TODO("Not yet implemented")
    }

    fun insertNodeInto(
        child: TreeNode<V>,
        parent: TreeNode<V>,
        index: Int,
    ) {
        TODO("Not yet implemented")
    }

    fun nodeStructureChanged(node: TreeNode<V>)

    fun getPathToRoot(node: TreeNode<V>): Array<TreeNode<V>>

    fun getIndexOfChild(
        parent: TreeNode<V>,
        node: TreeNode<V>,
    ): Int {
        TODO("Not yet implemented")
    }

    fun getChild(
        parent: TreeNode<V>,
        index: Int,
    ): TreeNode<V> {
        TODO("Not yet implemented")
    }

    fun getChildCount(parent: TreeNode<V>): Int {
        TODO("Not yet implemented")
    }

    fun isLeaf(node: TreeNode<V>): Boolean {
        TODO("Not yet implemented")
    }

    fun valueForPathChanged(
        path: TreePath?,
        newValue: TreeNode<V>,
    ) {
        TODO("Not yet implemented")
    }

    fun addTreeModelListener(l: TreeModelListener) {
        TODO("Not yet implemented")
    }

    fun removeTreeModelListener(l: TreeModelListener) {
        TODO("Not yet implemented")
    }
}

open class DefaultTreeModel<V>(
    override var root: TreeNode<V>,
) : TreeModel<V> {
    @JvmField
    protected val listenerList: EventListenerList = TODO("Not yet implemented")

    override fun nodeStructureChanged(node: TreeNode<V>) {
        TODO("Not yet implemented")
    }

    override fun getPathToRoot(node: TreeNode<V>): Array<TreeNode<V>> {
        TODO("Not yet implemented")
    }
}
