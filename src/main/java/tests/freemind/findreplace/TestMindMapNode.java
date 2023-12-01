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
import java.util.SortedMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import ch.d4span.freemind.mindmap.MindMap;
import ch.d4span.freemind.mindmap.MindMapNode;
import ch.d4span.freemind.treemodel.MutableTreeNode;
import ch.d4span.freemind.treemodel.TreeNode;
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
import freemind.modes.attributes.Attribute;

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
    public boolean hasFoldedParents() {
		if(isRoot())
			return false;
		if(getParentNode().isFolded()) {
			return true;
		}
		return getParentNode().hasFoldedParents();
	}

	@Override
    public String getObjectId(ModeController controller) {
		return null;
	}

	@Override
    public ListIterator childrenFolded() {
		return children.listIterator();
	}

	@Override
    public ListIterator childrenUnfolded() {
		return children.listIterator();
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
    public int getChildPosition(MindMapNode childNode) {
		return children.indexOf(childNode);
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
    public Color getColor() {
		return null;
	}

	@Override
    public String getStyle() {
		return null;
	}

	@Override
    public void setStyle(String style) {
	}

	@Override
    public boolean hasStyle() {
		return false;
	}

	@Override
    public MindMapNode getParentNode() {
		return mNewParent;
	}

	@Override
    public boolean isBold() {
		return false;
	}

	@Override
    public boolean isItalic() {
		return false;
	}

	@Override
    public boolean isUnderlined() {
		return false;
	}

	@Override
    public Font getFont() {
		return null;
	}

	@Override
    public String getFontSize() {
		return null;
	}

	@Override
    public String getFontFamilyName() {
		return null;
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

	@Override
    public boolean isFolded() {
		return false;
	}

	@Override
    public boolean isLeft() {
		return false;
	}

	public boolean isOnLeftSideOfRoot() {
		return false;
	}

	@Override
    public void setLeft(boolean isLeft) {
	}

	@Override
    public void setFolded(boolean folded) {
	}

	@Override
    public void setFont(Font font) {
	}

	@Override
    public void setShiftY(int y) {
	}

	@Override
    public int getShiftY() {
		return 0;
	}

	@Override
    public int calcShiftY() {
		return 0;
	}

	@Override
    public void setVGap(int i) {
	}

	@Override
    public int getVGap() {
		return 0;
	}

	public int calcVGap() {
		return 0;
	}

	@Override
    public void setHGap(int i) {
	}

	@Override
    public int getHGap() {
		return 0;
	}

	@Override
    public void setLink(String link) {
	}

	@Override
    public void setFontSize(int fontSize) {
	}

	@Override
    public void setColor(Color color) {
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
    public Color getBackgroundColor() {
		return null;
	}

	@Override
    public void setBackgroundColor(Color color) {
	}

	@Override
    public List<PermanentNodeHook> getHooks() {
		return null;
	}

	@Override
    public Collection<PermanentNodeHook> getActivatedHooks() {
		return null;
	}

	@Override
    public PermanentNodeHook addHook(PermanentNodeHook hook) {
		return null;
	}

	@Override
    public void invokeHook(NodeHook hook) {
	}

	@Override
    public void removeHook(PermanentNodeHook hook) {
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
    public void setUserObject(Object object) {
	}

	@Override
    public void removeFromParent() {
	}

	@Override
    public void setParent(MutableTreeNode newParent) {
		mNewParent = (TestMindMapNode) newParent;
	}

	@Override
    public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
    public int getChildCount() {
		return children.size();
	}

	@Override
    public TreeNode getParent() {
		return mNewParent;
	}

	@Override
    public int getIndex(TreeNode node) {
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

	public Attribute getAttribute(int pPosition) {
		return null;
	}

	public List<String> getAttributeKeyList() {
		return null;
	}
	
	@Override
	public List<Attribute> getAttributes() {
		return null;
	}

	public int getAttributePosition(String key) {
		return 0;
	}

	public void setAttribute(int pPosition, Attribute pAttribute) {
	}

	public int getAttributeTableLength() {
		return 0;
	}

	public EventListenerList getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNewChildLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createAttributeTableModel() {
		// TODO Auto-generated method stub

	}

	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWriteable() {
		return true;
	}

	public boolean isDescendantOfOrEqual(MindMapNode pParentNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemind.modes.MindMapNode#removeAllHooks()
	 */
	public void removeAllHooks() {
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#sortedChildrenUnfolded()
	 */
	public ListIterator sortedChildrenUnfolded() {
		return null;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#hasVisibleChilds()
	 */
	public boolean hasVisibleChilds() {
		return false;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#addAttribute(freemind.modes.attributes.Attribute)
	 */
	@Override
	public int addAttribute(Attribute pAttribute) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#removeAttribute(int)
	 */
	@Override
	public void removeAttribute(int pPosition) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#insertAttribute(int, freemind.modes.attributes.Attribute)
	 */
	@Override
	public void insertAttribute(int pPosition, Attribute pAttribute) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#getMap()
	 */
	@Override
	public MindMap getMap() {
		return null;
	}

	@Override
	public boolean isStrikethrough() {
		return false;
	}

	@Override
	public String getBareStyle() {
		return null;
	}

}
