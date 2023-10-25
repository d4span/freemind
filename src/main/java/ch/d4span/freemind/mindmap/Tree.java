package ch.d4span.freemind.mindmap;

import java.util.Enumeration;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public interface Tree<T extends Tree.TreeNode> {
  T getRoot();

  T getChild(T var1, int var2);

  int getChildCount(T var1);

  boolean isLeaf(T var1);

  void valueForPathChanged(TreePath var1, T var2);

  int getIndexOfChild(T var1, T var2);

  void addTreeModelListener(TreeModelListener var1);

  void removeTreeModelListener(TreeModelListener var1);

  interface TreeNode {
    TreeNode getChildAt(int var1);

    int getChildCount();

    TreeNode getParent();

    int getIndex(TreeNode var1);

    boolean getAllowsChildren();

    boolean isLeaf();

    Enumeration<? extends TreeNode> children();
  }
}
