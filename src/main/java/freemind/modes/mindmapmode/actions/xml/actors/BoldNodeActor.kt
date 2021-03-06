/*FreeMind - A Program for creating and viewing Mindmaps
*Copyright (C) 2000-2014 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitri Polivaev and others.
*
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
package freemind.modes.mindmapmode.actions.xml.actors

import freemind.controller.actions.generated.instance.BoldNodeAction
import freemind.controller.actions.generated.instance.XmlAction
import freemind.modes.ExtendedMapFeedback
import freemind.modes.MindMap
import freemind.modes.MindMapNode
import freemind.modes.mindmapmode.actions.xml.ActionPair

/**
 * @author foltin
 * @date 16.03.2014
 */
class BoldNodeActor
/**
 * @param pMapFeedback
 */
(pMapFeedback: ExtendedMapFeedback?) : NodeXmlActorAdapter(pMapFeedback) {
    override fun act(action: XmlAction) {
        if (action is BoldNodeAction) {
            val boldact = action
            val node = getNodeFromID(boldact.node)
            if (node?.isBold != boldact.bold) {
                node?.isBold = boldact.bold
                exMapFeedback?.nodeChanged(node)
            }
        }
    }

    override fun getDoActionClass(): Class<BoldNodeAction> {
        return BoldNodeAction::class.java
    }

    override fun apply(model: MindMap, selected: MindMapNode): ActionPair {
        // every node is set to the inverse of the focussed node.
        val bold = selected.isBold
        return getActionPair(selected, !bold)
    }

    private fun getActionPair(selected: MindMapNode, bold: Boolean): ActionPair {
        val boldAction = toggleBold(selected, bold)
        val undoBoldAction = toggleBold(selected, selected.isBold)
        return ActionPair(boldAction, undoBoldAction)
    }

    private fun toggleBold(selected: MindMapNode, bold: Boolean): BoldNodeAction {
        val boldAction = BoldNodeAction()
        boldAction.node = getNodeID(selected)
        boldAction.bold = bold
        return boldAction
    }

    fun setBold(node: MindMapNode, bold: Boolean) {
        execute(getActionPair(node, bold))
    }
}
