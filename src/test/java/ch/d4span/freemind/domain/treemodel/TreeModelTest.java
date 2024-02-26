package ch.d4span.freemind.domain.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A TreeModel")
public interface TreeModelTest {

  TreeModel<?> constructTreeModel();

  @Test
  @DisplayName("should have a root node.")
  default void testRoot() {
    var treeModel = constructTreeModel();
    assertThat(treeModel.root()).isNotNull();
  }

  @Test
  @DisplayName("should return the path to root.")
  default void testPathToRoot() {
    var treeModel = constructTreeModel();
    var node = mock(TreeNode.class);
    var path = treeModel.pathToRoot(node);
    assertThat(path).isNotNull();
  }

  @Test
  @DisplayName("should return the index of a child.")
  default void testIndexOfChild() {
    var treeModel = constructTreeModel();
    var parent = mock(TreeNode.class);
    var child = mock(TreeNode.class);
    var index = treeModel.indexOfChild(parent, child);
    assertThat(index).isGreaterThanOrEqualTo(0);
  }

  @Test
  @DisplayName("should return the child at the given index.")
  default void testChild() {
    var treeModel = constructTreeModel();
    var parent = mock(TreeNode.class);
    var index = 0;
    var child = treeModel.child(parent, index);
    assertThat(child).isNotNull();
  }

  @Test
  @DisplayName("should return the number of children.")
  default void testChildCount() {
    var treeModel = constructTreeModel();
    var parent = mock(TreeNode.class);
    var count = treeModel.childCount(parent);
    assertThat(count).isGreaterThanOrEqualTo(0);
  }

  @Test
  @DisplayName("should return whether the node is a leaf.")
  default void testLeaf() {
    var treeModel = constructTreeModel();
    var node = mock(TreeNode.class);
    var isLeaf = treeModel.leaf(node);
    assertThat(isLeaf).isNotNull();
  }
}
