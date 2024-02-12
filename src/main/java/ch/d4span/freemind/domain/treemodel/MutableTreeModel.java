package ch.d4span.freemind.domain.treemodel;

public interface MutableTreeModel<V> extends TreeModel<V> {

  default void removeNodeFromParent(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void insertNodeInto(TreeNode<V> child, TreeNode<V> parent, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  void nodeStructureChanged(TreeNode<V> node);
}
