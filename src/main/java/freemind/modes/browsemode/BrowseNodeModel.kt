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
/*$Id: BrowseNodeModel.java,v 1.7.18.1.14.2 2008/05/26 19:25:07 christianfoltin Exp $*/
package freemind.modes.browsemode

import freemind.main.XMLElement
import freemind.modes.MindMap
import freemind.modes.NodeAdapter
import java.util.LinkedList

/**
 * This class represents a single Node of a Tree. It contains direct handles to
 * its parent and children and to its view.
 */
open class BrowseNodeModel(userObject: Any?, map: MindMap?) : NodeAdapter(userObject, map) {
    //
    // Constructors
    //
    init {
        children = LinkedList()
        edge = BrowseEdgeModel(this, mapFeedback)
    }

    //
    // The mandatory load and save methods
    //
    // NanoXML save method
    fun save(): XMLElement? {
        return null
    }

    override fun isWriteable(): Boolean {
        return false
    }
}
