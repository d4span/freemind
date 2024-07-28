package org.freemind.tree;

import java.util.SequencedCollection;

public interface TreeNode<T> {
  TreeNode<T> getChildAt(int index);

  int getChildCount();

  TreeNode<T> getParent();

  int getIndex(TreeNode<T> child);

  boolean getAllowsChildren();

  boolean isLeaf();

  SequencedCollection<TreeNode<T>> children();

  T getUserObject();
}
