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

package freemind.modes.mindmapmode.actions.xml.actors;

import ch.d4span.freemind.domain.mindmap.MindMap;
import ch.d4span.freemind.domain.mindmap.MindMapNode;
import freemind.controller.actions.generated.instance.BoldNodeAction;
import freemind.controller.actions.generated.instance.XmlAction;
import freemind.modes.ExtendedMapFeedback;
import freemind.modes.NodeAdapter;
import freemind.modes.mindmapmode.actions.xml.ActionPair;

/**
 * @author foltin
 * @date 16.03.2014
 */
public class BoldNodeActor extends NodeXmlActorAdapter {

	/**
	 * @param pMapFeedback
	 */
	public BoldNodeActor(ExtendedMapFeedback pMapFeedback) {
		super(pMapFeedback);
	}

	@Override
	public void act(XmlAction action) {
		if (action instanceof BoldNodeAction boldact) {
			NodeAdapter node = getNodeFromID(boldact.getNode());
			if (node.isBold() != boldact.getBold()) {
				node.setBold(boldact.getBold());
				mMapFeedback.nodeChanged(node);
			}
		}
	}

	@Override
	public Class<BoldNodeAction> getDoActionClass() {
		return BoldNodeAction.class;
	}

	@Override
	public ActionPair apply(MindMap model, MindMapNode selected) {
		// every node is set to the inverse of the focussed node.
		boolean bold = ((NodeAdapter) getSelected()).isBold();
		return getActionPair((NodeAdapter) selected, !bold);
	}

	private ActionPair getActionPair(NodeAdapter selected, boolean bold) {
		BoldNodeAction boldAction = toggleBold(selected, bold);
		BoldNodeAction undoBoldAction = toggleBold(selected, selected.isBold());
		return new ActionPair(boldAction, undoBoldAction);
	}

	private BoldNodeAction toggleBold(MindMapNode selected, boolean bold) {
		BoldNodeAction boldAction = new BoldNodeAction();
		boldAction.setNode(getNodeID(selected));
		boldAction.setBold(bold);
		return boldAction;
	}

	public void setBold(MindMapNode node, boolean bold) {
		execute(getActionPair((NodeAdapter) node, bold));
	}
}
