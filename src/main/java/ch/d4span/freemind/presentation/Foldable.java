package ch.d4span.freemind.presentation;

import ch.d4span.freemind.domain.mindmap.MindMapNode;
import java.util.ListIterator;

public interface Foldable {

  boolean isFolded();

	void setFolded(boolean folded);

  ListIterator<MindMapNode> childrenFolded();

  ListIterator<MindMapNode> childrenUnfolded();

  ListIterator<MindMapNode> sortedChildrenUnfolded();

	boolean hasFoldedParents();

}
