package ch.d4span.freemind.domain.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Instancio.create;
import static org.instancio.Instancio.createList;

import java.util.List;
import java.util.NoSuchElementException;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
@DisplayName("Tests the default implementations of TreeNode's methods.")
class TreeNodeTest {

  record TreeNodeImpl<T>(T value, List<TreeNode<T>> children) implements TreeNode<T> {
    @Override
    public List<TreeNode<T>> getChildren() {
      return children();
    }

    @Override
    public EventListenerList getListeners() {
      return new EventListenerList();
    }
  }

  @RepeatedTest(10)
  @DisplayName("Tests default childCount() implementation.")
  void testGetChildCount() {
    var children = createChildren();
    var node = new TreeNodeImpl<String>(create(String.class), children);
    assertThat(node.childCount()).isEqualTo(children.size());
  }

  @RepeatedTest(10)
  @DisplayName("Tests default isLeaf() implementation.")
  void testIsLeaf() {
    var hasChildren = create(Boolean.class);
    var node =
        new TreeNodeImpl<String>(create(String.class), hasChildren ? createChildren() : List.of());
    assertThat(node.isLeaf()).isEqualTo(!hasChildren);
  }

  @RepeatedTest(10)
  @DisplayName("Tests default childAt() implementation.")
  void testChildAt() {
    var children = createChildren();
    var node = new TreeNodeImpl<String>(create(String.class), children);

    for (int i = 0; i < children.size(); i++) {
      assertThat(node.childAt(i)).isEqualTo(children.get(i));
    }
  }

  @RepeatedTest(10)
  @DisplayName("Tests default isLeaf() implementation.")
  void testIndexOf() {
    var children = createChildren();
    var node = new TreeNodeImpl<String>(create(String.class), children);

    for (int i = 0; i < children.size(); i++) {
      var child = children.get(i);
      int indexOfChild = node.indexOf(child);
      assertThat(indexOfChild).isEqualTo(i);
    }

    var notAChild = new TreeNodeImpl<String>(create(String.class), List.of());
    assertThatThrownBy(() -> node.indexOf(notAChild)).isInstanceOf(NoSuchElementException.class);
  }

  private List<TreeNode<String>> createChildren() {
    return createList(String.class).stream()
        .map(s -> (TreeNode<String>) new TreeNodeImpl<String>(s, List.of()))
        .toList();
  }
}
