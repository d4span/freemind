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


package ch.d4span.freemind.domain.mindmap;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import ch.d4span.freemind.domain.treemodel.MutableTreeNode;
import freemind.controller.filter.FilterInfo;
import freemind.main.XMLElement;
import freemind.modes.HistoryInformation;
import freemind.modes.MapFeedback;
import freemind.modes.MindIcon;
import freemind.modes.MindMapCloud;
import freemind.modes.MindMapEdge;
import freemind.modes.MindMapLinkRegistry;
import freemind.modes.ModeController;

public interface MindMapNode extends MutableTreeNode {

	String getText();

	void setText(String text);

	String getXmlText();

	void setXmlText(String structuredText);

	String getPlainTextContent();

	String getXmlNoteText();

	void setXmlNoteText(String structuredNoteText);

	String getNoteText();

	void setNoteText(String noteText);

	List<MindMapNode> getChildren();

	boolean hasChildren();

	Optional<Integer> getChildPosition(MindMapNode childNode);

	boolean isWriteable();

	int getNodeLevel();

	MindMapNode getParentNode();

	MindMap getMap();

	boolean isDescendantOf(MindMapNode node);

	boolean isDescendantOfOrEqual(MindMapNode pParentNode);

	boolean isRoot();

	public FilterInfo getFilterInfo();

	/**
	 * returns a short textual description of the text contained in the node.
	 * Html is filtered out.
	 */
	String getShortText(ModeController controller);

	MindMapEdge getEdge();

	// returns false if and only if the style is inherited from parent

	@Override
    String toString();

	TreePath getPath();

	String getLink();

	void setLink(String link);

	// fc, 06.10.2003:
	/** Is a vector of MindIcon s */
	List<MindIcon> getIcons();

	void addIcon(MindIcon icon, int position);

	/* @return returns the new amount of icons. */
	int removeIcon(int position);

	// end, fc, 24.9.2003

	// clouds, fc, 08.11.2003:
	MindMapCloud getCloud();

	void setCloud(MindMapCloud cloud);

	// end clouds.

	// fc, 24.2.2004: background color:

	// tooltips,fc 29.2.2004
	void setToolTip(String key, String tip);

	SortedMap<String, String> getToolTip();

	// additional info, fc, 15.12.2004

	/**
	 * This method can be used to store non-visual additions to a node.
	 * Currently, it is used for encrypted nodes to store the encrypted content.
	 */
	void setAdditionalInfo(String info);

	/**
	 * Is only used to store encrypted content of an encrypted mind map node.
	 * 
	 * @see MindMapNode.setAdditionalInfo(String)
	 */
	public String getAdditionalInfo();

	/**
	 * @return a flat copy of this node including all extras like notes, etc.
	 *         But the children are not copied!
	 */
	MindMapNode shallowCopy();

	/**
	 * @param saveHidden
	 *            TODO: Seems not to be used. Remove or fill with live.
	 * @param saveChildren
	 *            if true, the save recurses to all of the nodes children.
	 */
	public XMLElement save(Writer writer, MindMapLinkRegistry registry,
			boolean saveHidden, boolean saveChildren) throws IOException;

	// fc, 10.2.2005:
	/**
	 * State icons are icons that are not saved. They indicate that this node is
	 * special.
	 */
	Map<String, ImageIcon> getStateIcons();

	/**
	 * @param icon
	 *            use null to remove the state icon. Then it is not required,
	 *            that the key already exists.
	 */
	void setStateIcon(String key, ImageIcon icon);

	// fc, 11.4.2005:
	HistoryInformation getHistoryInformation();

	void setHistoryInformation(HistoryInformation historyInformation);

	boolean isVisible();

	/**
	 * @return true, if there is exactly one visible child.
	 */
	boolean hasExactlyOneVisibleChild();

	/**
	 * @return true, if there is at least one visible child.
	 */
	boolean hasVisibleChilds();
	
	MapFeedback getMapFeedback();

	boolean isNewChildLeft();
}
