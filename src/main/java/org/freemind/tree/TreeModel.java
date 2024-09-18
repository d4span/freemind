package org.freemind.tree;

public interface TreeModel<T> {
  TreeNode<T> getRoot();

  TreeNode<T> getChild(TreeNode<T> parent, int index);

  int getChildCount(TreeNode<T> node);

  boolean isLeaf(TreeNode<T> node);

  void valueForPathChanged(TreePath<T> var1, T var2);

  int getIndexOfChild(TreeNode<T> parent, TreeNode<T> child);

  void addTreeModelListener(TreeModelListener var1);

  void removeTreeModelListener(TreeModelListener var1);
}
