package ch.d4span.freemind.domain.treemodel;

import static java.util.Map.copyOf;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public record DefaultTreeModel<T>(Map<T, List<T>> treeStructure) implements TreeModel<T> {

  public DefaultTreeModel(Map<T, List<T>> treeStructure) {
    checkTreeStructure(treeStructure);
    this.treeStructure = copyOf(treeStructure);
  }

  @Override
  public TreeNode<T> root() {
    return new TreeNode<T>() {

      @Override
      public T value() {
        return null;
      }

      @Override
      public List<TreeNode<T>> getChildren() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChildren'");
      }

      @Override
      public EventListenerList getListeners() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListeners'");
      }
    };
  }

  @Override
  public TreeNode<T>[] getPathToRoot(TreeNode<T> node) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPathToRoot'");
  }

  private static <T> void checkTreeStructure(Map<T, List<T>> treeStructure) {
    var numberOfEdges = 0;
    var descendants = new HashSet<T>();
    var nodes = new HashSet<>(treeStructure.keySet());
    for (var entry : treeStructure.entrySet()) {
      numberOfEdges += entry.getValue().size();
      descendants.addAll(entry.getValue());
    }

    nodes.removeAll(descendants);

    var isValid =
        nodes.size() == 1
            && numberOfEdges == descendants.size()
            && numberOfEdges == treeStructure.size() - 1;
    if (!isValid) {
      throw new IllegalArgumentException("The tree structure is not valid.");
    }
  }
}
