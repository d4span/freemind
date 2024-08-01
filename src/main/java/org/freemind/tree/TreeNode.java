package org.freemind.tree;

import java.util.List;

public interface TreeNode<T> {
  TreeNode<T> getChildAt(int index);

  int getChildCount();

  TreeNode<T> getParent();

  int getIndex(TreeNode<T> child);

  boolean getAllowsChildren();

  boolean isLeaf();

  List<TreeNode<T>> children();

  T getValue();
}
