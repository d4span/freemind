/*FreeMind - A Program for creating and viewing Mindmaps
*Copyright (C) 2000-2014 Christian Foltin, Joerg Mueller, Daniel Polansky, Dimitri Polivaev and others.
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
import freemind.controller.actions.generated.instance.MoveNodeXmlAction;
import freemind.controller.actions.generated.instance.XmlAction;
import freemind.modes.ExtendedMapFeedback;
import freemind.modes.NodeAdapter;
import freemind.modes.mindmapmode.actions.xml.ActionPair;

/**
 * @author foltin
 * @date 27.03.2014
 */
public class MoveNodeActor extends NodeXmlActorAdapter {

	/**
	 * @param pMapFeedback
	 */
	public MoveNodeActor(ExtendedMapFeedback pMapFeedback) {
		super(pMapFeedback);
	}
	
	@Override
    public void act(XmlAction action) {
		MoveNodeXmlAction moveAction = (MoveNodeXmlAction) action;
		NodeAdapter node = getNodeFromID(moveAction.getNode());
		node.setHGap(moveAction.getHGap());
		node.setShiftY(moveAction.getShiftY());
		if (!node.isRoot())
		((NodeAdapter) node.getParentNode()).setVGap(moveAction.getVGap());
		getExMapFeedback().nodeChanged(node);
	}

	@Override
    public Class<MoveNodeXmlAction> getDoActionClass() {
		return MoveNodeXmlAction.class;
	}

	@Override
    public ActionPair apply(MindMap model, MindMapNode selected) {
		// reset position
		if (selected.isRoot())
			return null;
		return getActionPair((NodeAdapter) selected, NodeAdapter.VGAP, NodeAdapter.HGAP, 0);
	}

	private ActionPair getActionPair(NodeAdapter selected, int parentVGap,
			int hGap, int shiftY) {
		MoveNodeXmlAction moveAction = moveNode(selected, parentVGap, hGap,
				shiftY);
		MoveNodeXmlAction undoAction = moveNode(selected, ((NodeAdapter) selected
				.getParentNode()).getVGap(), selected.getHGap(),
				selected.getShiftY());
		return new ActionPair(moveAction, undoAction);
	}

	private MoveNodeXmlAction moveNode(MindMapNode selected, int parentVGap,
			int hGap, int shiftY) {
		MoveNodeXmlAction moveNodeAction = new MoveNodeXmlAction();
		moveNodeAction.setNode(getNodeID(selected));
		moveNodeAction.setHGap(hGap);
		moveNodeAction.setVGap(parentVGap);
		moveNodeAction.setShiftY(shiftY);
		return moveNodeAction;
	}

	public void moveNodeTo(MindMapNode node, int parentVGap, int hGap,
			int shiftY) {
		if (parentVGap == ((NodeAdapter) node.getParentNode()).getVGap()
				&& hGap == ((NodeAdapter) node).getHGap() && shiftY == ((NodeAdapter) node).getShiftY()) {
			return;
		}
		execute(getActionPair((NodeAdapter) node, parentVGap, hGap, shiftY));
	}


}
