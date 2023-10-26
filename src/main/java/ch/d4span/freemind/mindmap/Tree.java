package ch.d4span.freemind.mindmap;

import java.util.List;
import java.util.Optional;

public interface Tree<N> {
  N getRoot();

  N getChild(N node, int index);

  int getChildCount(N node);

  boolean isLeaf(N var1);

  void valueForPathChanged(TreePath var1, N value);

  Optional<Integer> getIndexOfChild(N node, N child);

  void addTreeModelListener(TreeModelListener<N> listener);

  void removeTreeModelListener(TreeModelListener<N> listener);

  interface TreePath {}

  interface TreeModelListener<N> {
    void treeNodesChanged(TreeModelEvent<N> e);

    void treeNodesInserted(TreeModelEvent<N> e);

    void treeNodesRemoved(TreeModelEvent<N> e);

    void treeStructureChanged(TreeModelEvent<N> e);

    record TreeModelEvent<D>(Tree<D> source, List<D> path, List<Integer> childIndices) {}
  }
}
