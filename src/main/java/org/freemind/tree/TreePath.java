package org.freemind.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreePath<T> {
  private TreePath<T> parentPath;
  private TreeNode<T> lastPathComponent;

  public TreePath(List<TreeNode<T>> path) {
    if (path != null && !path.isEmpty()) {
      Objects.requireNonNull(path.getLast());

      if (path.size() > 1) {
        this.parentPath = new TreePath<>(path, path.size() - 1);
      }
    } else {
      throw new IllegalArgumentException("path in TreePath must be non null and not empty.");
    }
  }

  public TreePath(TreeNode<T> lastPathComponent) {
    Objects.requireNonNull(lastPathComponent);

    this.lastPathComponent = lastPathComponent;
    this.parentPath = null;
  }

  protected TreePath(TreePath<T> parent, TreeNode<T> lastPathComponent) {
    Objects.requireNonNull(parent);

    this.parentPath = parent;
    this.lastPathComponent = lastPathComponent;
  }

  protected TreePath(List<TreeNode<T>> path, int length) {
    Objects.requireNonNull(path.getLast());

    if (length > 1) {
      this.parentPath = new TreePath<>(path, length - 1);
    }
  }

  protected TreePath() {}

  public List<TreeNode<T>> getPath() {
    int i = this.getPathCount();
    List<TreeNode<T>> result = new ArrayList<>(i--);

    for (var path = this; path != null; path = path.getParentPath()) {
      result.add(i--, path.getLastPathComponent());
    }

    return result;
  }

  public TreeNode<T> getLastPathComponent() {
    return this.lastPathComponent;
  }

  public int getPathCount() {
    int result = 0;

    for (var path = this; path != null; path = path.getParentPath()) {
      ++result;
    }

    return result;
  }

  public Object getPathComponent(int index) {
    int pathLength = this.getPathCount();
    if (index >= 0 && index < pathLength) {
      var path = this;

      for (int i = pathLength - 1; i != index; --i) {
        path = path.getParentPath();
      }

      return path.getLastPathComponent();
    } else {
      throw new IllegalArgumentException("Index " + index + " is out of the specified range");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (o instanceof TreePath oTreePath) {
      if (this.getPathCount() != oTreePath.getPathCount()) {
        return false;
      } else {
        for (var path = this; path != null; path = path.getParentPath()) {
          if (!path.getLastPathComponent().equals(oTreePath.getLastPathComponent())) {
            return false;
          }

          oTreePath = oTreePath.getParentPath();
        }

        return true;
      }
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return this.getLastPathComponent().hashCode();
  }

  public boolean isDescendant(TreePath<T> aTreePath) {
    if (aTreePath == this) {
      return true;
    } else if (aTreePath == null) {
      return false;
    } else {
      int pathLength = this.getPathCount();
      int oPathLength = aTreePath.getPathCount();
      if (oPathLength < pathLength) {
        return false;
      } else {
        while (oPathLength-- > pathLength) {
          aTreePath = aTreePath.getParentPath();
        }

        return this.equals(aTreePath);
      }
    }
  }

  public TreePath<T> pathByAddingChild(TreeNode<T> child) {
    Objects.requireNonNull(child);

    return new TreePath<>(this, child);
  }

  public TreePath<T> getParentPath() {
    return this.parentPath;
  }

  @Override
  public String toString() {
    StringBuilder tempSpot = new StringBuilder("[");
    int counter = 0;

    for (int maxCounter = this.getPathCount(); counter < maxCounter; ++counter) {
      if (counter > 0) {
        tempSpot.append(", ");
      }

      tempSpot.append(this.getPathComponent(counter));
    }

    tempSpot.append("]");
    return tempSpot.toString();
  }
}
