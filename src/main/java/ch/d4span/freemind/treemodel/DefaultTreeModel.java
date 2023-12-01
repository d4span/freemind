package ch.d4span.freemind.treemodel;

public class DefaultTreeModel<V> implements TreeModel<V> {
  private TreeNode<V> root;
  protected EventListenerList listenerList;

  public DefaultTreeModel(TreeNode<V> root) {
    this.root = root;
  }

  public void setRoot(TreeNode<V> root) {
    this.root = root;
  }

  @Override
  public TreeNode<V> getRoot() {
    return root;
  }

  @Override
  public void nodeStructureChanged(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public TreeNode<V>[] getPathToRoot(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
