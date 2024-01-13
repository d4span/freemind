/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2005  Joerg Mueller, Daniel Polansky, Christian Foltin and others.
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
 * Created on 02.05.2005
 */

package accessories.plugins.time;

import javax.swing.Action;
import javax.swing.JMenuItem;

import ch.d4span.freemind.domain.mindmap.MindMap;
import ch.d4span.freemind.domain.mindmap.MindMapNode;
import freemind.controller.MenuItemEnabledListener;
import freemind.extensions.HookRegistration;
import freemind.extensions.PermanentNodeHook;
import freemind.modes.ModeController;
import freemind.modes.NodeAdapter;
import freemind.modes.common.plugins.ReminderHookBase;
import freemind.modes.mindmapmode.actions.NodeHookAction;

/**
 * Enables the encrypt/decrypt menu item only if the map/node is encrypted.
 * 
 * @author foltin
 * 
 */
public class TimeManagementOrganizer implements HookRegistration,
		MenuItemEnabledListener {

	private final ModeController controller;
	private final java.util.logging.Logger logger;

	public TimeManagementOrganizer(ModeController controller, MindMap map) {
		this.controller = controller;
		logger = controller.getFrame().getLogger(this.getClass().getName());
	}

	@Override
    public void register() {
	}

	@Override
    public void deRegister() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * freemind.controller.MenuItemEnabledListener#isEnabled(javax.swing.JMenuItem
	 * , javax.swing.Action)
	 */
	@Override
    public boolean isEnabled(JMenuItem item, Action action) {
		if (action instanceof NodeHookAction) {
			String hookName = ((NodeHookAction) action).getHookName();
			if ("plugins/time/RemoveReminderHook.java".equals(hookName)) {
				boolean visible = false;
				for (MindMapNode node : controller.getSelecteds()) {
					if (TimeManagementOrganizer.getHook(node) != null) {
						visible = true;
					}
				}
				item.setVisible(visible);
			}
		}
		return true;
	}

	/**
	 */
	public static ReminderHookBase getHook(MindMapNode node) {
		for (PermanentNodeHook element : ((NodeAdapter) node).getActivatedHooks()) {
			if (element instanceof ReminderHookBase) {
				return (ReminderHookBase) element;
			}
		}
		return null;
	}
}