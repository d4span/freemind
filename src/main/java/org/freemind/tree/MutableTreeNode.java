package org.freemind.tree;

public interface MutableTreeNode<T> extends TreeNode<T> {
    void insert(TreeNode<T> child, int index);
 
    void remove(int index);
 
    void remove(TreeNode<T> child);
 
    void setUserObject(T value);
 
    void removeFromParent();
 
    void setParent(TreeNode<T> parent);
 }
