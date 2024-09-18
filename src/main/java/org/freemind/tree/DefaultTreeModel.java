package org.freemind.tree;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Vector;

public class DefaultTreeModel<T> implements Serializable, TreeModel<T> {
  protected TreeNode<T> root;
  protected EventListenerList listenerList;
  protected boolean asksAllowsChildren;

  public DefaultTreeModel(TreeNode<T> root) {
    this(root, false);
  }

  public DefaultTreeModel(TreeNode<T> root, boolean asksAllowsChildren) {
    this.listenerList = new EventListenerList();
    this.root = root;
    this.asksAllowsChildren = asksAllowsChildren;
  }

  public void setAsksAllowsChildren(boolean newValue) {
    this.asksAllowsChildren = newValue;
  }

  public boolean asksAllowsChildren() {
    return this.asksAllowsChildren;
  }

  public void setRoot(TreeNode<T> root) {
    var oldRoot = this.root;
    this.root = root;
    if (root == null && oldRoot != null) {
      this.fireTreeStructureChanged((TreePath<T>) null);
    } else {
      this.nodeStructureChanged(root);
    }
  }

  @Override
  public TreeNode<T> getRoot() {
    return this.root;
  }

  @Override
  public int getIndexOfChild(TreeNode<T> parent, TreeNode<T> child) {
    return parent != null && child != null ? parent.getIndex(child) : -1;
  }

  @Override
  public TreeNode<T> getChild(TreeNode<T> parent, int index) {
    return parent.getChildAt(index);
  }

  @Override
  public int getChildCount(TreeNode<T> parent) {
    return (parent).getChildCount();
  }

  @Override
  public boolean isLeaf(TreeNode<T> node) {
    if (this.asksAllowsChildren) {
      return !(node).getAllowsChildren();
    } else {
      return (node).isLeaf();
    }
  }

  public void reload() {
    this.reload(this.root);
  }

  @Override
  public void valueForPathChanged(TreePath<T> path, T newValue) {
    var aNode = (MutableTreeNode<T>) path.getLastPathComponent();
    aNode.setValue(newValue);
    this.nodeChanged(aNode);
  }

  public void insertNodeInto(MutableTreeNode<T> newChild, MutableTreeNode<T> parent, int index) {
    parent.insert(newChild, index);
    int[] newIndexs = new int[] {index};
    this.nodesWereInserted(parent, newIndexs);
  }

  public void removeNodeFromParent(MutableTreeNode<T> node) {
    var parent = (MutableTreeNode<T>) node.getParent();
    if (parent == null) {
      throw new IllegalArgumentException("node does not have a parent.");
    } else {
      int[] childIndex = new int[1];
      List<TreeNode<T>> removedArray = new ArrayList<>(1);
      childIndex[0] = parent.getIndex(node);
      parent.remove(childIndex[0]);
      removedArray.add(0, node);
      this.nodesWereRemoved(parent, childIndex, removedArray);
    }
  }

  public void nodeChanged(TreeNode<T> node) {
    if (this.listenerList != null && node != null) {
      var parent = node.getParent();
      if (parent != null) {
        int anIndex = parent.getIndex(node);
        if (anIndex != -1) {
          int[] cIndexs = new int[] {anIndex};
          this.nodesChanged(parent, cIndexs);
        }
      } else if (node == this.getRoot()) {
        this.nodesChanged(node, (int[]) null);
      }
    }
  }

  public void reload(TreeNode<T> node) {
    if (node != null) {
      this.fireTreeStructureChanged(
          this.getPathToRoot(node), (int[]) null, (List<TreeNode<T>>) null);
    }
  }

  public void nodesWereInserted(TreeNode<T> node, int[] childIndices) {
    if (this.listenerList != null
        && node != null
        && childIndices != null
        && childIndices.length > 0) {
      int cCount = childIndices.length;
      List<TreeNode<T>> newChildren = new ArrayList<>(cCount);

      for (int counter = 0; counter < cCount; ++counter) {
        newChildren.add(counter, node.getChildAt(childIndices[counter]));
      }

      this.fireTreeNodesInserted(this.getPathToRoot(node), childIndices, newChildren);
    }
  }

  public void nodesWereRemoved(
      TreeNode<T> node, int[] childIndices, List<TreeNode<T>> removedChildren) {
    if (node != null && childIndices != null) {
      this.fireTreeNodesRemoved(this.getPathToRoot(node), childIndices, removedChildren);
    }
  }

  public void nodesChanged(TreeNode<T> node, int[] childIndices) {
    if (node != null) {
      if (childIndices != null) {
        int cCount = childIndices.length;
        if (cCount > 0) {
          List<TreeNode<T>> cChildren = new ArrayList<>(cCount);

          for (int counter = 0; counter < cCount; ++counter) {
            cChildren.add(counter, node.getChildAt(childIndices[counter]));
          }

          this.fireTreeNodesChanged(this.getPathToRoot(node), childIndices, cChildren);
        }
      } else if (node == this.getRoot()) {
        this.fireTreeNodesChanged(
            this.getPathToRoot(node), (int[]) null, (List<TreeNode<T>>) null);
      }
    }
  }

  public void nodeStructureChanged(TreeNode<T> node) {
    if (node != null) {
      this.fireTreeStructureChanged(
          this.getPathToRoot(node), (int[]) null, (List<TreeNode<T>>) null);
    }
  }

  public List<TreeNode<T>> getPathToRoot(TreeNode<T> aNode) {
    return this.getPathToRoot(aNode, 0);
  }

  protected List<TreeNode<T>> getPathToRoot(TreeNode<T> aNode, int depth) {
    List<TreeNode<T>> retNodes;
    if (aNode == null) {
      if (depth == 0) {
        return emptyList();
      }

      retNodes = new ArrayList<>(depth);
    } else {
      ++depth;
      if (aNode == this.root) {
        retNodes = new ArrayList<>(depth);
      } else {
        retNodes = this.getPathToRoot(aNode.getParent(), depth);
      }

      retNodes.add(retNodes.size() - depth, aNode);
    }

    return retNodes;
  }

  @Override
  public void addTreeModelListener(TreeModelListener l) {
    this.listenerList.add(TreeModelListener.class, l);
  }

  @Override
  public void removeTreeModelListener(TreeModelListener l) {
    this.listenerList.remove(TreeModelListener.class, l);
  }

  public TreeModelListener[] getTreeModelListeners() {
    return (TreeModelListener[]) this.listenerList.getListeners(TreeModelListener.class);
  }

  protected void fireTreeNodesChanged(
      List<TreeNode<T>> path, int[] childIndices, List<TreeNode<T>> children) {
    Object[] listeners = this.listenerList.getListenerList();
    TreeModelEvent<T> e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        if (e == null) {
          e = new TreeModelEvent<>(path, childIndices, children);
        }

        ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
      }
    }
  }

  protected void fireTreeNodesInserted(
      List<TreeNode<T>> path, int[] childIndices, List<TreeNode<T>> children) {
    Object[] listeners = this.listenerList.getListenerList();
    TreeModelEvent<T> e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        if (e == null) {
          e = new TreeModelEvent<>(path, childIndices, children);
        }

        ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
      }
    }
  }

  protected void fireTreeNodesRemoved(
      List<TreeNode<T>> path, int[] childIndices, List<TreeNode<T>> children) {
    Object[] listeners = this.listenerList.getListenerList();
    TreeModelEvent<T> e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        if (e == null) {
          e = new TreeModelEvent<>(path, childIndices, children);
        }

        ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
      }
    }
  }

  protected void fireTreeStructureChanged(
      List<TreeNode<T>> path, int[] childIndices, List<TreeNode<T>> children) {
    Object[] listeners = this.listenerList.getListenerList();
    TreeModelEvent<T> e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        if (e == null) {
          e = new TreeModelEvent<>(path, childIndices, children);
        }

        ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
      }
    }
  }

  private void fireTreeStructureChanged(TreePath<T> path) {
    Object[] listeners = this.listenerList.getListenerList();
    TreeModelEvent<T> e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        if (e == null) {
          e = new TreeModelEvent<>(path);
        }

        ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
      }
    }
  }

  public <T extends EventListener> EventListener[] getListeners(Class<T> listenerType) {
    return this.listenerList.getListeners(listenerType);
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    Vector<Object> values = new Vector();
    s.defaultWriteObject();
    if (this.root instanceof Serializable) {
      values.addElement("root");
      values.addElement(this.root);
    }

    s.writeObject(values);
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    ObjectInputStream.GetField f = s.readFields();
    EventListenerList newListenerList = (EventListenerList) f.get("listenerList", (Object) null);
    if (newListenerList == null) {
      throw new InvalidObjectException("Null listenerList");
    } else {
      this.listenerList = newListenerList;
      this.asksAllowsChildren = f.get("asksAllowsChildren", false);
      Vector<?> values = (Vector) s.readObject();
      int indexCounter = 0;
      int maxCounter = values.size();
      if (indexCounter < maxCounter && "root".equals(values.elementAt(indexCounter))) {
        ++indexCounter;
        TreeNode newRoot = (TreeNode) values.elementAt(indexCounter);
        if (newRoot == null) {
          throw new InvalidObjectException("Null root");
        }

        this.root = newRoot;
        ++indexCounter;
      }
    }
  }
}
