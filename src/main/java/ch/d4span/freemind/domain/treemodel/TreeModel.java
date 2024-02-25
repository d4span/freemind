package ch.d4span.freemind.domain.treemodel;

public interface TreeModel<ID> {
  TreeNode<ID> root();

  TreeNode<ID>[] pathToRoot(TreeNode<ID> node);

  default int indexOfChild(TreeNode<ID> parent, TreeNode<ID> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default TreeNode<ID> child(TreeNode<ID> parent, int index) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default int childCount(TreeNode<ID> parent) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  default boolean leaf(TreeNode<ID> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
