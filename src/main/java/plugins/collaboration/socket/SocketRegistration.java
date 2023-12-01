/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2009  Christian Foltin and others.
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
 *
 * Created on 28.01.2009
 */
/*$Id: DatabaseRegistration.java,v 1.1.2.1 2009/02/04 19:31:21 christianfoltin Exp $*/

package plugins.collaboration.socket;

import java.util.Collection;

import javax.swing.Action;
import javax.swing.JMenuItem;

import ch.d4span.freemind.mindmap.MindMap;
import freemind.controller.MenuItemEnabledListener;
import freemind.controller.MenuItemSelectedListener;
import freemind.extensions.HookRegistration;
import freemind.extensions.PermanentNodeHook;
import freemind.modes.ModeController;
import freemind.modes.mindmapmode.MindMapController;
import freemind.modes.mindmapmode.actions.NodeHookAction;

public class SocketRegistration implements HookRegistration,
		MenuItemSelectedListener, MenuItemEnabledListener {

	private final MindMapController mController;
	private final java.util.logging.Logger logger;

	public SocketRegistration(ModeController controller, MindMap map) {
		this.mController = (MindMapController) controller;
		logger = controller.getFrame().getLogger(this.getClass().getName());
	}

	@Override
    public void register() {
		logger.fine("Registration of database registration.");
	}

	@Override
    public void deRegister() {
		logger.fine("Deregistration of database registration.");
	}

	@Override
    public boolean isSelected(JMenuItem pCheckItem, Action pAction) {
		logger.fine(this + " is asked for " + pAction + ".");
		if (pAction instanceof NodeHookAction action) {
			if (MindMapMaster.LABEL.equals(action.getHookName())) {
				return isMaster();
			}
			if (MindMapClient.SLAVE_STARTER_LABEL.equals(action.getHookName())) {
				return isSlave();
			}
			if (MindMapClient.SLAVE_PUBLISH_LABEL.equals(action.getHookName())) {
				// the publish map item is not selected. It would be nice to get
				// the information, though.
				return false;
			}
		}
		return false;
	}

	private boolean isMaster() {
		Collection<PermanentNodeHook> activatedHooks = mController.getRootNode()
				.getActivatedHooks();
		for (PermanentNodeHook hook : activatedHooks) {
			if (hook instanceof MindMapMaster) {
				return true;
			}
		}
		return false;
	}

	private boolean isSlave() {
		Collection<PermanentNodeHook> activatedHooks = mController.getRootNode()
				.getActivatedHooks();
		for (PermanentNodeHook hook : activatedHooks) {
			if (hook instanceof SocketConnectionHook) {
				return true;
			}
		}
		return false;
	}

	/**
	 * When one option is enabled, the other is impossible.
	 * */
	@Override
    public boolean isEnabled(JMenuItem pItem, Action pAction) {
		logger.fine(this + " is asked for " + pAction + ".");
		if (pAction instanceof NodeHookAction action) {
			if (MindMapMaster.LABEL.equals(action.getHookName())) {
				return !isSlave();
			}
			// not available, if a master is active.
			if (MindMapClient.SLAVE_STARTER_LABEL.equals(action.getHookName())) {
				return !isMaster();
			}
			if (MindMapClient.SLAVE_PUBLISH_LABEL.equals(action.getHookName())) {
				// this is for the publish map command. Only available, if
				// neither nor.
				return !isSlave() && !isMaster();
			}
		}
		throw new IllegalArgumentException("Unknown action: " + pAction);
	}
}
