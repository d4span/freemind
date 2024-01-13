package ch.d4span.freemind.domain.treemodel;

public record EventListenerList() {
  public Object[] getListenerList() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void add(Class<TreeModelListener> class1, TreeModelListener l) {
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  public void remove(Class<TreeModelListener> class1, TreeModelListener l) {
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }
}
