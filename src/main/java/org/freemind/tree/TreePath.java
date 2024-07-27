package org.freemind.tree;

import java.io.Serializable;

public class TreePath implements Serializable {
    private TreePath parentPath;
    private Object lastPathComponent;
 
    public TreePath(Object[] path) {
       if (path != null && path.length != 0) {
          this.lastPathComponent = path[path.length - 1];
          if (this.lastPathComponent == null) {
             throw new IllegalArgumentException("Last path component must be non-null");
          } else {
             if (path.length > 1) {
                this.parentPath = new TreePath(path, path.length - 1);
             }
 
          }
       } else {
          throw new IllegalArgumentException("path in TreePath must be non null and not empty.");
       }
    }
 
    public TreePath(Object lastPathComponent) {
       if (lastPathComponent == null) {
          throw new IllegalArgumentException("path in TreePath must be non null.");
       } else {
          this.lastPathComponent = lastPathComponent;
          this.parentPath = null;
       }
    }
 
    protected TreePath(TreePath parent, Object lastPathComponent) {
       if (lastPathComponent == null) {
          throw new IllegalArgumentException("path in TreePath must be non null.");
       } else {
          this.parentPath = parent;
          this.lastPathComponent = lastPathComponent;
       }
    }
 
    protected TreePath(Object[] path, int length) {
       this.lastPathComponent = path[length - 1];
       if (this.lastPathComponent == null) {
          throw new IllegalArgumentException("Path elements must be non-null");
       } else {
          if (length > 1) {
             this.parentPath = new TreePath(path, length - 1);
          }
 
       }
    }
 
    protected TreePath() {
    }
 
    public Object[] getPath() {
       int i = this.getPathCount();
       Object[] result = new Object[i--];
 
       for(TreePath path = this; path != null; path = path.getParentPath()) {
          result[i--] = path.getLastPathComponent();
       }
 
       return result;
    }
 
    public Object getLastPathComponent() {
       return this.lastPathComponent;
    }
 
    public int getPathCount() {
       int result = 0;
 
       for(TreePath path = this; path != null; path = path.getParentPath()) {
          ++result;
       }
 
       return result;
    }
 
    public Object getPathComponent(int index) {
       int pathLength = this.getPathCount();
       if (index >= 0 && index < pathLength) {
          TreePath path = this;
 
          for(int i = pathLength - 1; i != index; --i) {
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
             for(TreePath path = this; path != null; path = path.getParentPath()) {
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
 
    public boolean isDescendant(TreePath aTreePath) {
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
             while(oPathLength-- > pathLength) {
                aTreePath = aTreePath.getParentPath();
             }
 
             return this.equals(aTreePath);
          }
       }
    }
 
    public TreePath pathByAddingChild(Object child) {
       if (child == null) {
          throw new NullPointerException("Null child not allowed");
       } else {
          return new TreePath(this, child);
       }
    }
 
    public TreePath getParentPath() {
       return this.parentPath;
    }
 
    @Override
    public String toString() {
       StringBuilder tempSpot = new StringBuilder("[");
       int counter = 0;
 
       for(int maxCounter = this.getPathCount(); counter < maxCounter; ++counter) {
          if (counter > 0) {
             tempSpot.append(", ");
          }
 
          tempSpot.append(this.getPathComponent(counter));
       }
 
       tempSpot.append("]");
       return tempSpot.toString();
    }
 }
