package org.freemind.tree;

public interface MutableTreeNode<T> extends TreeNode<T> {
  void insert(MutableTreeNode<T> child, int index);

  void remove(int index);

  void remove(MutableTreeNode<T> child);

  void setValue(T value);

  void removeFromParent();

  void setParent(MutableTreeNode<T> parent);
}
