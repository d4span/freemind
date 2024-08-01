package org.freemind.tree;

public interface TreeModel<T> {
  TreeNode<T> getRoot();

  Object getChild(TreeNode<T> var1, int var2);

  int getChildCount(TreeNode<T> var1);

  boolean isLeaf(TreeNode<T> var1);

  void valueForPathChanged(TreePath var1, Object var2);

  int getIndexOfChild(TreeNode<T> var1, TreeNode<T> var2);

  void addTreeModelListener(TreeModelListener var1);

  void removeTreeModelListener(TreeModelListener var1);
}
