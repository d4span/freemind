package ch.d4span.freemind.domain.treemodel;

public interface TreeModelListener {
  interface TreeModelEvent {
    int[] getChildIndices();
  }

  void treeNodesChanged(TreeModelEvent e);

  void treeNodesInserted(TreeModelEvent e);

  void treeNodesRemoved(TreeModelEvent e);

  void treeStructureChanged(TreeModelEvent e);
}
