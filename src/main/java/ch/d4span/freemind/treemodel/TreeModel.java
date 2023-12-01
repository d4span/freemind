package ch.d4span.freemind.treemodel;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public interface TreeModel<V> {
  TreeNode<V> getRoot();

  default void removeNodeFromParent(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void insertNodeInto(TreeNode<V> child, TreeNode<V> parent, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  void nodeStructureChanged(TreeNode<V> node);

  TreeNode<V>[] getPathToRoot(TreeNode<V> node);

  default int getIndexOfChild(TreeNode<V> parent, TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default TreeNode<V> getChild(TreeNode<V> parent, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default int getChildCount(TreeNode<V> parent) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default boolean isLeaf(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void valueForPathChanged(TreePath path, TreeNode<V> newValue) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void addTreeModelListener(TreeModelListener l) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void removeTreeModelListener(TreeModelListener l) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
