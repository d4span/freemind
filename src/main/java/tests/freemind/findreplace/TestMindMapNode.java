/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2006  Christian Foltin <christianfoltin@users.sourceforge.net>
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
/*$Id: TestMindMapNode.java,v 1.1.2.17 2008/05/26 19:25:09 christianfoltin Exp $*/

package tests.freemind.findreplace;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import ch.d4span.freemind.domain.mindmap.MindMap;
import ch.d4span.freemind.domain.mindmap.MindMapNode;
import ch.d4span.freemind.domain.treemodel.EventListenerList;
import ch.d4span.freemind.domain.treemodel.MutableTreeNode;
import ch.d4span.freemind.domain.treemodel.TreeModelListener;
import ch.d4span.freemind.domain.treemodel.TreeNode;
import freemind.controller.filter.FilterInfo;
import freemind.extensions.NodeHook;
import freemind.extensions.PermanentNodeHook;
import freemind.main.XMLElement;
import freemind.modes.HistoryInformation;
import freemind.modes.MapFeedback;
import freemind.modes.MindIcon;
import freemind.modes.MindMapCloud;
import freemind.modes.MindMapEdge;
import freemind.modes.MindMapLinkRegistry;
import freemind.modes.ModeController;

public final class TestMindMapNode implements MindMapNode {
	private String text = "";
	private Vector<TestMindMapNode> children = new Vector<TestMindMapNode>();
	private TestMindMapNode mNewParent;

	@Override
    public String getText() {
		return text;
	}

	@Override
    public void setText(String text) {
		this.text = text;
	}

	@Override
    public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
    public FilterInfo getFilterInfo() {
		return null;
	}

	@Override
    public Optional<Integer> getChildPosition(MindMapNode childNode) {
		return Optional.of(children.indexOf(childNode));
	}

	public MindMapNode getPreferredChild() {
		return null;
	}

	public void setPreferredChild(MindMapNode node) {
	}

	@Override
    public int getNodeLevel() {
		return 0;
	}

	@Override
    public String getLink() {
		return null;
	}

	@Override
    public String getShortText(ModeController controller) {
		return null;
	}

	@Override
    public MindMapEdge getEdge() {
		return null;
	}

	@Override
    public MindMapNode getParentNode() {
		return mNewParent;
	}

	@Override
    public String getPlainTextContent() {
		return null;
	}

	@Override
    public TreePath getPath() {
		return null;
	}

	@Override
    public boolean isDescendantOf(MindMapNode node) {
		return false;
	}

	@Override
    public boolean isRoot() {
		return false;
	}

	public boolean isOnLeftSideOfRoot() {
		return false;
	}

	public int calcVGap() {
		return 0;
	}

	@Override
    public void setLink(String link) {
	}

	@Override
    public List<MindIcon> getIcons() {
		return null;
	}

	@Override
    public void addIcon(MindIcon icon, int position) {
	}

	@Override
    public int removeIcon(int position) {
		return 0;
	}

	@Override
    public MindMapCloud getCloud() {
		return null;
	}

	@Override
    public void setCloud(MindMapCloud cloud) {
	}

	@Override
    public void setToolTip(String key, String tip) {
	}

	@Override
    public SortedMap<String, String> getToolTip() {
		return null;
	}

	@Override
    public void setAdditionalInfo(String info) {
	}

	@Override
    public String getAdditionalInfo() {
		return null;
	}

	@Override
    public MindMapNode shallowCopy() {
		return null;
	}

	@Override
    public XMLElement save(Writer writer, MindMapLinkRegistry registry,
			boolean saveHidden, boolean saveChildren) throws IOException {
		return null;
	}

	@Override
    public Map<String, ImageIcon> getStateIcons() {
		return null;
	}

	@Override
    public void setStateIcon(String key, ImageIcon icon) {
	}

	@Override
    public HistoryInformation getHistoryInformation() {
		return null;
	}

	@Override
    public void setHistoryInformation(HistoryInformation historyInformation) {
	}

	@Override
    public boolean isVisible() {
		return false;
	}

	@Override
    public boolean hasExactlyOneVisibleChild() {
		return false;
	}

	@Override
    public MapFeedback getMapFeedback() {
		return null;
	}

	@Override
    public void addTreeModelListener(TreeModelListener l) {
	}

	@Override
    public void removeTreeModelListener(TreeModelListener l) {
	}

	@Override
    public void insert(MutableTreeNode child, int index) {
		children.insertElementAt((TestMindMapNode) child, index);
	}

	public void remove(int index) {
		children.remove(index);
	}

	@Override
    public void remove(MutableTreeNode node) {
		children.remove(node);
	}

	@Override
    public void removeFromParent() {
	}

	@Override
    public void setParent(MutableTreeNode newParent) {
		mNewParent = (TestMindMapNode) newParent;
	}

	@Override
    public TreeNode childAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
    public int childCount() {
		return children.size();
	}

	@Override
    public TreeNode getParent() {
		return mNewParent;
	}

	@Override
    public int indexOf(TreeNode node) {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	@Override
    public boolean isLeaf() {
		return false;
	}

	public Enumeration children() {
		return children.elements();
	}

	@Override
    public String getXmlText() {
		return null;
	}

	@Override
    public void setXmlText(String structuredText) {
	}

	@Override
    public String getXmlNoteText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void setXmlNoteText(String structuredNoteText) {
		// TODO Auto-generated method stub

	}

	@Override
    public List getChildren() {
		return children;
	}

	@Override
    public String getNoteText() {
		return null;
	}

	@Override
    public void setNoteText(String noteText) {
	}

	@Override
    public EventListenerList getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean isNewChildLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createAttributeTableModel() {
		// TODO Auto-generated method stub

	}

	@Override
    public boolean isWriteable() {
		return true;
	}

	@Override
    public boolean isDescendantOfOrEqual(MindMapNode pParentNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#hasVisibleChilds()
	 */
	@Override
    public boolean hasVisibleChilds() {
		return false;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#getMap()
	 */
	@Override
	public MindMap getMap() {
		return null;
	}
}
