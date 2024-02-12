package ch.d4span.freemind.domain.treemodel;

import java.util.List;
import java.util.NoSuchElementException;

public interface TreeNode<V> {
  default V value() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default int childCount() {
    return getChildren().size();
  }

  default boolean isLeaf() {
    return getChildren().isEmpty();
  }

  default int indexOf(TreeNode<V> child) {
    int indexOfChild = getChildren().indexOf(child);
    if (indexOfChild == -1) {
      throw new NoSuchElementException();
    }

    return indexOfChild;
  }

  default TreeNode<V> childAt(int index) {
    return getChildren().get(index);
  }

  default TreeNode<V> getParent() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  List<TreeNode<V>> getChildren();

  EventListenerList getListeners();
}
