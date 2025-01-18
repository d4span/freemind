/*
 * Copyright (c) 1997, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.github.d4span.freemind

import java.util.*

/**
 * Encapsulates information describing changes to a tree model, and
 * used to notify tree model listeners of the change.
 * For more information and examples see
 * [How to Write a Tree Model Listener](https://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html),
 * a section in *The Java Tutorial.*
 *
 *
 * **Warning:**
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans
 * has been added to the `java.beans` package.
 * Please see [java.beans.XMLEncoder].
 *
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
// Same-version serialization only
class TreeModelEvent (source: Any, private val theChildren: Array<Any?>, var treePath: TreePath?, private val theChildIndices: IntArray?) : EventObject(source) {
    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * an array of Objects. All of the modified objects are siblings which are
     * direct descendents (not grandchildren) of the specified parent.
     * The positions at which the inserts, deletes, or changes occurred are
     * specified by an array of `int`. The indexes in that array
     * must be in order, from lowest to highest.
     *
     *
     * For changes, the indexes in the model correspond exactly to the indexes
     * of items currently displayed in the UI. As a result, it is not really
     * critical if the indexes are not in their exact order. But after multiple
     * inserts or deletes, the items currently in the UI no longer correspond
     * to the items in the model. It is therefore critical to specify the
     * indexes properly for inserts and deletes.
     *
     *
     * For inserts, the indexes represent the *final* state of the tree,
     * after the inserts have occurred. Since the indexes must be specified in
     * order, the most natural processing methodology is to do the inserts
     * starting at the lowest index and working towards the highest. Accumulate
     * a Vector of `Integer` objects that specify the
     * insert-locations as you go, then convert the Vector to an
     * array of `int` to create the event. When the position-index
     * equals zero, the node is inserted at the beginning of the list. When the
     * position index equals the size of the list, the node is "inserted" at
     * (appended to) the end of the list.
     *
     *
     * For deletes, the indexes represent the *initial* state of the tree,
     * before the deletes have occurred. Since the indexes must be specified in
     * order, the most natural processing methodology is to use a delete-counter.
     * Start by initializing the counter to zero and start work through the
     * list from lowest to highest. Every time you do a delete, add the current
     * value of the delete-counter to the index-position where the delete occurred,
     * and append the result to a Vector of delete-locations, using
     * `addElement()`. Then increment the delete-counter. The index
     * positions stored in the Vector therefore reflect the effects of all previous
     * deletes, so they represent each object's position in the initial tree.
     * (You could also start at the highest index and working back towards the
     * lowest, accumulating a Vector of delete-locations as you go using the
     * `insertElementAt(Integer, 0)`.) However you produce the Vector
     * of initial-positions, you then need to convert the Vector of `Integer`
     * objects to an array of `int` to create the event.
     *
     *
     * **Notes:**
     *  * Like the `insertNodeInto` method in the
     * `DefaultTreeModel` class, `insertElementAt`
     * appends to the `Vector` when the index matches the size
     * of the vector. So you can use `insertElementAt(Integer, 0)`
     * even when the vector is empty.
     *  * To create a node changed event for the root node, specify the parent
     * and the child indices as `null`.
     *
     *
     * @param source the Object responsible for generating the event (typically
     * the creator of the event object passes `this`
     * for its value)
     * @param path   an array of Object identifying the path to the
     * parent of the modified item(s), where the first element
     * of the array is the Object stored at the root node and
     * the last element is the Object stored at the parent node
     * @param childIndices an array of `int` that specifies the
     * index values of the removed items. The indices must be
     * in sorted order, from lowest to highest
     * @param children an array of Object containing the inserted, removed, or
     * changed objects
     * @see TreePath
     */
    constructor(
        source: Any, path: Array<TreeNode?>?, childIndices: IntArray?,
        children: Array<Any?>
    ) : this(source, if (path == null) null else TreePath(path), childIndices, children)

    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * a TreePath object. For more information on how to specify the indexes
     * and objects, see
     * `TreeModelEvent(Object,Object[],int[],Object[])`.
     *
     * @param source the Object responsible for generating the event (typically
     * the creator of the event object passes `this`
     * for its value)
     * @param path   a TreePath object that identifies the path to the
     * parent of the modified item(s)
     * @param childIndices an array of `int` that specifies the
     * index values of the modified items
     * @param children an array of Object containing the inserted, removed, or
     * changed objects
     *
     * @see .TreeModelEvent
     */
    constructor(
        source: Any, path: TreePath?, childIndices: IntArray?,
        children: Array<Any?>
    ) : this(source = source, theChildren = children, treePath = path, theChildIndices = childIndices)

    /**
     * Used to create an event when the node structure has changed in some way,
     * identifying the path to the root of a modified subtree as an array of
     * Objects. A structure change event might involve nodes swapping position,
     * for example, or it might encapsulate multiple inserts and deletes in the
     * subtree stemming from the node, where the changes may have taken place at
     * different levels of the subtree.
     * <blockquote>
     * **Note:**<br></br>
     * JTree collapses all nodes under the specified node, so that only its
     * immediate children are visible.
    </blockquote> *
     *
     * @param source the Object responsible for generating the event (typically
     * the creator of the event object passes `this`
     * for its value)
     * @param path   an array of Object identifying the path to the root of the
     * modified subtree, where the first element of the array is
     * the object stored at the root node and the last element
     * is the object stored at the changed node
     * @see TreePath
     */
    constructor(source: Any, path: Array<Any?>?) : this(source, if (path == null) null else TreePath(path))

    /**
     * Used to create an event when the node structure has changed in some way,
     * identifying the path to the root of the modified subtree as a TreePath
     * object. For more information on this event specification, see
     * `TreeModelEvent(Object,Object[])`.
     *
     * @param source the Object responsible for generating the event (typically
     * the creator of the event object passes `this`
     * for its value)
     * @param path   a TreePath object that identifies the path to the
     * change. In the DefaultTreeModel,
     * this object contains an array of user-data objects,
     * but a subclass of TreePath could use some totally
     * different mechanism -- for example, a node ID number
     *
     * @see .TreeModelEvent
     */
    constructor(source: Any, path: TreePath?) : this(source = source, theChildren = arrayOfNulls<Any?>(0), treePath = path, theChildIndices = null)

    /**
     * Convenience method to get the array of objects from the TreePath
     * instance that this event wraps.
     *
     * @return an array of Objects, where the first Object is the one
     * stored at the root and the last object is the one
     * stored at the node identified by the path
     */
    fun getPath(): Array<Any?>? {
        if (this.treePath != null) return treePath!!.path
        return null
    }

    /**
     * Returns the objects that are children of the node identified by
     * `getPath` at the locations specified by
     * `getChildIndices`. If this is a removal event the
     * returned objects are no longer children of the parent node.
     *
     * @return an array of Object containing the children specified by
     * the event
     * @see .getPath
     *
     * @see .getChildIndices
     */
    fun getChildren(): Array<Any?>? {
        if (theChildren != null) {
            val cCount = theChildren!!.size
            val retChildren = arrayOfNulls<Any>(cCount)

            System.arraycopy(theChildren, 0, retChildren, 0, cCount)
            return retChildren
        }
        return null
    }

    /**
     * Returns the values of the child indexes. If this is a removal event
     * the indexes point to locations in the initial list where items
     * were removed. If it is an insert, the indices point to locations
     * in the final list where the items were added. For node changes,
     * the indices point to the locations of the modified nodes.
     *
     * @return an array of `int` containing index locations for
     * the children specified by the event
     */
    fun getChildIndices(): IntArray? {
        if (theChildIndices != null) {
            val cCount = theChildIndices!!.size
            val retArray = IntArray(cCount)

            System.arraycopy(theChildIndices, 0, retArray, 0, cCount)
            return retArray
        }
        return null
    }

    /**
     * Returns a string that displays and identifies this object's
     * properties.
     *
     * @return a String representation of this object
     */
    override fun toString(): String {
        val sb = StringBuilder()

        sb.append(javaClass.getName() + " " + hashCode())
        if (this.treePath != null) sb.append(" path " + this.treePath)
        if (theChildIndices != null) {
            sb.append(" indices [ ")
            for (counter in theChildIndices!!.indices) sb.append(theChildIndices!![counter].toString() + " ")
            sb.append("]")
        }
        if (theChildren != null) {
            sb.append(" children [ ")
            for (counter in theChildren.indices) sb.append(theChildren!![counter].toString() + " ")
            sb.append("]")
        }
        return sb.toString()
    }
}