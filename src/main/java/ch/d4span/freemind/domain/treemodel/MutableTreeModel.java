package ch.d4span.freemind.domain.treemodel;

public interface MutableTreeModel<ID> extends TreeModel<ID> {

  default void removeNodeFromParent(TreeNode<ID> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default void insertNodeInto(TreeNode<ID> child, TreeNode<ID> parent, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  void nodeStructureChanged(TreeNode<ID> node);
}
