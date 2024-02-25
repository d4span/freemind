package ch.d4span.freemind.domain.treemodel;

public class DefaultTreeModel<V> implements MutableTreeModel<V> {
  private TreeNode<V> root;
  
  protected EventListenerList listenerList;

  public DefaultTreeModel(TreeNode<V> root) {
    this.root = root;
  }

  public void setRoot(TreeNode<V> root) {
    this.root = root;
  }

  @Override
  public TreeNode<V> root() {
    return root;
  }

  @Override
  public void nodeStructureChanged(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public TreeNode<V>[] pathToRoot(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
