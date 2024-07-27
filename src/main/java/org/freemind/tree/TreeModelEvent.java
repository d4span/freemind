package org.freemind.tree;

public class TreeModelEvent {
  protected TreePath path;
  protected int[] childIndices;
  protected Object[] children;
  private Object source;

  public TreeModelEvent(Object source, Object[] path, int[] childIndices, Object[] children) {
    this(source, path == null ? null : new TreePath(path), childIndices, children);
  }

  public TreeModelEvent(Object source, TreePath path, int[] childIndices, Object[] children) {
    this.source = source;
    this.path = path;
    this.childIndices = childIndices;
    this.children = children;
  }

  public TreeModelEvent(Object source, Object[] path) {
    this(source, path == null ? null : new TreePath(path));
  }

  public TreeModelEvent(Object source, TreePath path) {
    this.source = source;
    this.path = path;
    this.childIndices = new int[0];
  }

  public TreePath getTreePath() {
    return this.path;
  }

  public Object[] getPath() {
    return this.path != null ? this.path.getPath() : null;
  }

  public Object[] getChildren() {
    if (this.children != null) {
      int cCount = this.children.length;
      Object[] retChildren = new Object[cCount];
      System.arraycopy(this.children, 0, retChildren, 0, cCount);
      return retChildren;
    } else {
      return null;
    }
  }

  public int[] getChildIndices() {
    if (this.childIndices != null) {
      int cCount = this.childIndices.length;
      int[] retArray = new int[cCount];
      System.arraycopy(this.childIndices, 0, retArray, 0, cCount);
      return retArray;
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String var10001 = this.getClass().getName();
    sb.append(var10001 + " " + this.hashCode());
    if (this.path != null) {
      sb.append(" path " + String.valueOf(this.path));
    }

    int counter;
    if (this.childIndices != null) {
      sb.append(" indices [ ");

      for (counter = 0; counter < this.childIndices.length; ++counter) {
        int var3 = this.childIndices[counter];
        sb.append("" + var3 + " ");
      }

      sb.append("]");
    }

    if (this.children != null) {
      sb.append(" children [ ");

      for (counter = 0; counter < this.children.length; ++counter) {
        Object var4 = this.children[counter];
        sb.append(String.valueOf(var4) + " ");
      }

      sb.append("]");
    }

    return sb.toString();
  }
}
