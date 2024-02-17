package ch.d4span.freemind.domain.treemodel;

public interface TreeNode<V> {
  int getChildCount();

  default boolean isLeaf() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default int getIndex(TreeNode<V> child) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default TreeNode<V> getChildAt(int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default TreeNode<V> getParent() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  EventListenerList getListeners();
}
