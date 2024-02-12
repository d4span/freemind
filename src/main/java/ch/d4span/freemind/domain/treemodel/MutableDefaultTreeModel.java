package ch.d4span.freemind.domain.treemodel;

public class MutableDefaultTreeModel<V> implements MutableTreeModel<V> {
  private TreeNode<V> root;
  
  protected EventListenerList listenerList;

  public MutableDefaultTreeModel(TreeNode<V> root) {
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
  public TreeNode<V>[] getPathToRoot(TreeNode<V> node) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
