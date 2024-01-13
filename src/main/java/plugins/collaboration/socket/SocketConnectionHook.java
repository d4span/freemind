/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2008  Joerg Mueller, Daniel Polansky, Christian Foltin and others.
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
 *DatabaseGNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Created on 28.12.2008
 */


package plugins.collaboration.socket;

import java.io.IOException;
import java.io.Writer;

import ch.d4span.freemind.domain.mindmap.MindMapNode;
import freemind.controller.actions.generated.instance.CollaborationUserInformation;
import freemind.extensions.DontSaveMarker;
import freemind.extensions.PermanentNodeHook;
import freemind.main.XMLElement;
import freemind.modes.ExtendedMapFeedback;
import freemind.view.mindmapview.NodeView;

/**
 * @author foltin
 * 
 */
public class SocketConnectionHook extends SocketBasics implements
		PermanentNodeHook, DontSaveMarker {

	private ClientCommunication mClientCommunication;

	/**
     *
     */

	@Override
    public void startupMapHook() {
		super.startupMapHook();
		// this is the internal call. do nothing
		logger.info("Startup of the permanent hook.");
		return;
	}

	@Override
    public void loadFrom(XMLElement pChild) {
		// this plugin should not be saved.
	}

	@Override
    public void save(XMLElement pXml) {
		// this plugin should not be saved.
		// nothing to do.
	}

	@Override
    public void shutdownMapHook() {
		deregisterFilter();
		// this is the internal call. shutdown
		logger.info("Shut down of the permanent hook.");
		if (mClientCommunication != null) {
			mClientCommunication.shutdown();
		}
		super.shutdownMapHook();
	}

	@Override
    public void onAddChild(MindMapNode pAddedChildNode) {
	}

	@Override
    public void onAddChildren(MindMapNode pAddedChild) {
	}

	@Override
    public void onLostFocusNode(NodeView pNodeView) {
	}

	@Override
    public void onNewChild(MindMapNode pNewChildNode) {
	}

	@Override
    public void onRemoveChild(MindMapNode pOldChildNode) {
	}

	@Override
    public void onRemoveChildren(MindMapNode pOldChildNode, MindMapNode pOldDad) {
	}

	@Override
    public void onFocusNode(NodeView pNodeView) {
	}

	@Override
    public void onUpdateChildrenHook(MindMapNode pUpdatedNode) {
	}

	@Override
    public void onUpdateNodeHook() {
	}

	@Override
    public void onViewCreatedHook(NodeView pNodeView) {
	}

	@Override
    public void onViewRemovedHook(NodeView pNodeView) {
	}

	@Override
    public Integer getRole() {
		return ROLE_SLAVE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugins.collaboration.socket.SocketBasics#getPort()
	 */
	@Override
    public int getPort() {
		return mClientCommunication.getPort();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugins.collaboration.socket.SocketBasics#lock()
	 */
	@Override
    protected String lock(String pUserName, ExtendedMapFeedback pController) throws UnableToGetLockException,
			InterruptedException {
		return mClientCommunication.sendLockRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plugins.collaboration.socket.SocketBasics#broadcastCommand(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
    protected void broadcastCommand(String pDoAction, String pUndoAction,
			String pLockId, ExtendedMapFeedback pController) throws Exception {
		mClientCommunication.sendCommand(pDoAction, pUndoAction, pLockId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugins.collaboration.socket.SocketBasics#unlock()
	 */
	@Override
    protected void unlock(ExtendedMapFeedback pController) {
	}

	/**
	 * @param pClientCommunication
	 */
	public void setClientCommunication(ClientCommunication pClientCommunication) {
		mClientCommunication = pClientCommunication;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugins.collaboration.socket.SocketBasics#shutdown()
	 */
	public void shutdown() {
		mClientCommunication.shutdown();
	}

	public ClientCommunication getClientCommunication() {
		return mClientCommunication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugins.collaboration.socket.SocketBasics#getMasterInformation()
	 */
	@Override
    public CollaborationUserInformation getMasterInformation(ExtendedMapFeedback pController) {
		if(mClientCommunication != null) {
			return mClientCommunication.getUserInfo();
		}
		return new CollaborationUserInformation();
	}

	/* (non-Javadoc)
	 * @see freemind.extensions.PermanentNodeHook#processUnfinishedLinks()
	 */
	@Override
    public void processUnfinishedLinks() {
	}

	@Override
    public void saveHtml(Writer pFileout) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
