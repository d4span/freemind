package org.freemind.tree;

import java.util.Arrays;
import java.util.List;

public class TreeModelEvent<T> {
  protected TreePath<T> path;
  protected int[] childIndices;
  protected List<TreeNode<T>> children;

  public TreeModelEvent(
      List<TreeNode<T>> path, int[] childIndices, List<TreeNode<T>> children) {
    this(path == null ? null : new TreePath<>(path), childIndices, children);
  }

  public TreeModelEvent(TreePath<T> path, int[] childIndices, List<TreeNode<T>> children) {
    this.path = path;
    this.childIndices = childIndices;
    this.children = children;
  }

  public TreeModelEvent(List<TreeNode<T>> path) {
    this(path == null ? null : new TreePath<>(path));
  }

  public TreeModelEvent(TreePath<T> path) {
    this.path = path;
    this.childIndices = new int[0];
  }

  public TreePath<T> getTreePath() {
    return this.path;
  }

  public List<TreeNode<T>> getPath() {
    return this.path != null ? this.path.getPath() : null;
  }

  public List<TreeNode<T>> getChildren() {
    if (this.children != null) {
      return List.copyOf(this.children);
    } else {
      return List.of();
    }
  }

  public int[] getChildIndices() {
    if (this.childIndices != null) {
      return Arrays.copyOf(this.childIndices, this.childIndices.length);
    } else {
      return new int[0];
    }
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    var var10001 = this.getClass().getName();
    sb.append(var10001 + " " + this.hashCode());
    if (this.path != null) {
      sb.append(" path " + this.path);
    }

    if (this.childIndices != null) {
      sb.append(" indices [ ");

      for (var var3 : this.childIndices) {
        sb.append("" + var3 + " ");
      }

      sb.append("]");
    }

    if (this.children != null) {
      sb.append(" children [ ");

      for (var var4 : this.children) {
        sb.append(String.valueOf(var4) + " ");
      }

      sb.append("]");
    }

    return sb.toString();
  }
}
