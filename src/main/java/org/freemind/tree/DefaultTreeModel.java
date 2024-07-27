package org.freemind.tree;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EventListener;
import java.util.Vector;

public class DefaultTreeModel implements Serializable, TreeModel {
    protected TreeNode root;
    protected EventListenerList listenerList;
    protected boolean asksAllowsChildren;
 
    public DefaultTreeModel(TreeNode root) {
       this(root, false);
    }
 
    public DefaultTreeModel(TreeNode root, boolean asksAllowsChildren) {
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
 
    public void setRoot(TreeNode root) {
       Object oldRoot = this.root;
       this.root = root;
       if (root == null && oldRoot != null) {
          this.fireTreeStructureChanged(this, (TreePath)null);
       } else {
          this.nodeStructureChanged(root);
       }
 
    }
 
    @Override
    public Object getRoot() {
       return this.root;
    }
 
    @Override
    public int getIndexOfChild(Object parent, Object child) {
       return parent != null && child != null ? ((TreeNode)parent).getIndex((TreeNode)child) : -1;
    }
 
    @Override
    public Object getChild(Object parent, int index) {
       return ((TreeNode)parent).getChildAt(index);
    }
 
    @Override
    public int getChildCount(Object parent) {
       return ((TreeNode)parent).getChildCount();
    }
 
    @Override
    public boolean isLeaf(Object node) {
       if (this.asksAllowsChildren) {
          return !((TreeNode)node).getAllowsChildren();
       } else {
          return ((TreeNode)node).isLeaf();
       }
    }
 
    public void reload() {
       this.reload(this.root);
    }
 
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
       MutableTreeNode aNode = (MutableTreeNode)path.getLastPathComponent();
       aNode.setUserObject(newValue);
       this.nodeChanged(aNode);
    }
 
    public void insertNodeInto(MutableTreeNode newChild, MutableTreeNode parent, int index) {
       parent.insert(newChild, index);
       int[] newIndexs = new int[]{index};
       this.nodesWereInserted(parent, newIndexs);
    }
 
    public void removeNodeFromParent(MutableTreeNode node) {
       MutableTreeNode parent = (MutableTreeNode)node.getParent();
       if (parent == null) {
          throw new IllegalArgumentException("node does not have a parent.");
       } else {
          int[] childIndex = new int[1];
          Object[] removedArray = new Object[1];
          childIndex[0] = parent.getIndex(node);
          parent.remove(childIndex[0]);
          removedArray[0] = node;
          this.nodesWereRemoved(parent, childIndex, removedArray);
       }
    }
 
    public void nodeChanged(TreeNode node) {
       if (this.listenerList != null && node != null) {
          TreeNode parent = node.getParent();
          if (parent != null) {
             int anIndex = parent.getIndex(node);
             if (anIndex != -1) {
                int[] cIndexs = new int[]{anIndex};
                this.nodesChanged(parent, cIndexs);
             }
          } else if (node == this.getRoot()) {
             this.nodesChanged(node, (int[])null);
          }
       }
 
    }
 
    public void reload(TreeNode node) {
       if (node != null) {
          this.fireTreeStructureChanged(this, this.getPathToRoot(node), (int[])null, (Object[])null);
       }
 
    }
 
    public void nodesWereInserted(TreeNode node, int[] childIndices) {
       if (this.listenerList != null && node != null && childIndices != null && childIndices.length > 0) {
          int cCount = childIndices.length;
          Object[] newChildren = new Object[cCount];
 
          for(int counter = 0; counter < cCount; ++counter) {
             newChildren[counter] = node.getChildAt(childIndices[counter]);
          }
 
          this.fireTreeNodesInserted(this, this.getPathToRoot(node), childIndices, newChildren);
       }
 
    }
 
    public void nodesWereRemoved(TreeNode node, int[] childIndices, Object[] removedChildren) {
       if (node != null && childIndices != null) {
          this.fireTreeNodesRemoved(this, this.getPathToRoot(node), childIndices, removedChildren);
       }
 
    }
 
    public void nodesChanged(TreeNode node, int[] childIndices) {
       if (node != null) {
          if (childIndices != null) {
             int cCount = childIndices.length;
             if (cCount > 0) {
                Object[] cChildren = new Object[cCount];
 
                for(int counter = 0; counter < cCount; ++counter) {
                   cChildren[counter] = node.getChildAt(childIndices[counter]);
                }
 
                this.fireTreeNodesChanged(this, this.getPathToRoot(node), childIndices, cChildren);
             }
          } else if (node == this.getRoot()) {
             this.fireTreeNodesChanged(this, this.getPathToRoot(node), (int[])null, (Object[])null);
          }
       }
 
    }
 
    public void nodeStructureChanged(TreeNode node) {
       if (node != null) {
          this.fireTreeStructureChanged(this, this.getPathToRoot(node), (int[])null, (Object[])null);
       }
 
    }
 
    public TreeNode[] getPathToRoot(TreeNode aNode) {
       return this.getPathToRoot(aNode, 0);
    }
 
    protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
       TreeNode[] retNodes;
       if (aNode == null) {
          if (depth == 0) {
             return null;
          }
 
          retNodes = new TreeNode[depth];
       } else {
          ++depth;
          if (aNode == this.root) {
             retNodes = new TreeNode[depth];
          } else {
             retNodes = this.getPathToRoot(aNode.getParent(), depth);
          }
 
          retNodes[retNodes.length - depth] = aNode;
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
       return (TreeModelListener[])this.listenerList.getListeners(TreeModelListener.class);
    }
 
    protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
       Object[] listeners = this.listenerList.getListenerList();
       TreeModelEvent e = null;
 
       for(int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TreeModelListener.class) {
             if (e == null) {
                e = new TreeModelEvent(source, path, childIndices, children);
             }
 
             ((TreeModelListener)listeners[i + 1]).treeNodesChanged(e);
          }
       }
 
    }
 
    protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
       Object[] listeners = this.listenerList.getListenerList();
       TreeModelEvent e = null;
 
       for(int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TreeModelListener.class) {
             if (e == null) {
                e = new TreeModelEvent(source, path, childIndices, children);
             }
 
             ((TreeModelListener)listeners[i + 1]).treeNodesInserted(e);
          }
       }
 
    }
 
    protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
       Object[] listeners = this.listenerList.getListenerList();
       TreeModelEvent e = null;
 
       for(int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TreeModelListener.class) {
             if (e == null) {
                e = new TreeModelEvent(source, path, childIndices, children);
             }
 
             ((TreeModelListener)listeners[i + 1]).treeNodesRemoved(e);
          }
       }
 
    }
 
    protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
       Object[] listeners = this.listenerList.getListenerList();
       TreeModelEvent e = null;
 
       for(int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TreeModelListener.class) {
             if (e == null) {
                e = new TreeModelEvent(source, path, childIndices, children);
             }
 
             ((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
          }
       }
 
    }
 
    private void fireTreeStructureChanged(Object source, TreePath path) {
       Object[] listeners = this.listenerList.getListenerList();
       TreeModelEvent e = null;
 
       for(int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == TreeModelListener.class) {
             if (e == null) {
                e = new TreeModelEvent(source, path);
             }
 
             ((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
          }
       }
 
    }
 
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
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
       EventListenerList newListenerList = (EventListenerList)f.get("listenerList", (Object)null);
       if (newListenerList == null) {
          throw new InvalidObjectException("Null listenerList");
       } else {
          this.listenerList = newListenerList;
          this.asksAllowsChildren = f.get("asksAllowsChildren", false);
          Vector<?> values = (Vector)s.readObject();
          int indexCounter = 0;
          int maxCounter = values.size();
          if (indexCounter < maxCounter && "root".equals(values.elementAt(indexCounter))) {
             ++indexCounter;
             TreeNode newRoot = (TreeNode)values.elementAt(indexCounter);
             if (newRoot == null) {
                throw new InvalidObjectException("Null root");
             }
 
             this.root = newRoot;
             ++indexCounter;
          }
 
       }
    }
 }
