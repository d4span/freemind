package ch.d4span.freemind.domain.treemodel;

public interface MutableTreeNode<V> extends TreeNode<V> {
  default void remove(MutableTreeNode<V> child) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void removeFromParent() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void insert(MutableTreeNode<V> child, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void setParent(MutableTreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void addTreeModelListener(TreeModelListener l) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void removeTreeModelListener(TreeModelListener l) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
