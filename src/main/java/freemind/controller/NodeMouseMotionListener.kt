/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2001  Joerg Mueller <joergmueller@bigfoot.com>
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*$Id: NodeMouseMotionListener.java,v 1.15.14.3 2006/01/12 23:10:12 christianfoltin Exp $*/
package freemind.controller

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

/**
 * The MouseMotionListener which belongs to every NodeView
 */
class NodeMouseMotionListener : MouseMotionListener, MouseListener {
    interface NodeMouseMotionObserver : MouseMotionListener, MouseListener {
        fun updateSelectionMethod()
    }

    private var mListener: NodeMouseMotionObserver? = null
    fun register(listener: NodeMouseMotionObserver?) {
        mListener = listener
    }

    fun deregister() {
        mListener = null
    }

    override fun mouseClicked(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseClicked(e)
    }

    override fun mouseDragged(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseDragged(e)
    }

    override fun mouseEntered(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseEntered(e)
    }

    override fun mouseExited(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseExited(e)
    }

    override fun mouseMoved(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseMoved(e)
    }

    override fun mousePressed(e: MouseEvent) {
        if (mListener != null) mListener!!.mousePressed(e)
    }

    override fun mouseReleased(e: MouseEvent) {
        if (mListener != null) mListener!!.mouseReleased(e)
    }

    fun updateSelectionMethod() {
        if (mListener != null) mListener!!.updateSelectionMethod()
    }
}
