package ch.d4span.freemind.domain.treemodel;

import static ch.d4span.freemind.domain.treemodel.TreeStructureTestUtils.treeStructureGeneratorFor;
import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;
import static org.instancio.Instancio.ofList;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

@DisplayName("A TreeModel")
public interface TreeModelTest {

  <T> TreeModel<T> constructTreeModelFrom(Map<T, List<T>> treeStructure);

  @RepeatedTest(10)
  @DisplayName("should have a root node.")
  default void testRoot() {
    var treeStructure = treeStructureGeneratorFor(Integer.class).apply(20);
    var treeModel = constructTreeModelFrom(treeStructure);

    assertThat(treeModel.root().value()).isEqualTo(rootOf(treeStructure));
  }

  @RepeatedTest(10)
  @DisplayName("should return the path to root.")
  default void testPathToRoot() {
    var treeStructure = treeStructureGeneratorFor(String.class).apply(20);
    var treeModel = constructTreeModelFrom(treeStructure);

    assertThat(treeModel.pathToRoot(treeModel.root())).hasSize(1).contains(treeModel.root());

    TreeNode<String>[] path = treeModel.pathToRoot(node);
    assertThat(path).isNotNull();
  }

  @RepeatedTest(10)
  @DisplayName("should return the index of a child.")
  default void testIndexOfChild() {
    var treeModel = constructTreeModelFrom();
    var parent = mock(TreeNode.class);
    var child = mock(TreeNode.class);
    var index = treeModel.indexOfChild(parent, child);
    assertThat(index).isGreaterThanOrEqualTo(0);
  }

  @RepeatedTest(10)
  @DisplayName("should return the child at the given index.")
  default void testChild() {
    var treeModel = constructTreeModelFrom();
    var parent = mock(TreeNode.class);
    var index = 0;
    var child = treeModel.child(parent, index);
    assertThat(child).isNotNull();
  }

  @RepeatedTest(10)
  @DisplayName("should return the number of children.")
  default void testChildCount() {
    var treeModel = constructTreeModelFrom();
    var parent = mock(TreeNode.class);
    var count = treeModel.childCount(parent);
    assertThat(count).isGreaterThanOrEqualTo(0);
  }

  @RepeatedTest(10)
  @DisplayName("should return whether the node is a leaf.")
  default void testLeaf() {
    var treeModel = constructTreeModelFrom();
    var node = mock(TreeNode.class);
    var isLeaf = treeModel.leaf(node);
    assertThat(isLeaf).isNotNull();
  }

  public static <T> Function<Integer, Map<T, List<T>>> treeStructureGeneratorFor(
      Class<T> elementType) {
    return (Integer maxSize) -> {
      var treeSize = abs(create(Integer.class)) % 10;
      return ofList(elementType).size(treeSize).create().stream()
          .reduce(
              new HashMap<T, List<T>>(),
              (treeStructure, element) -> {
                if (!treeStructure.isEmpty()) {
                  var ancestors = treeStructure.keySet().stream().toList();
                  var ancestorIndex = Math.abs(create(Integer.class)) % ancestors.size();
                  treeStructure.get(ancestors.get(ancestorIndex)).add(element);
                }
                treeStructure.put(element, new ArrayList<>());

                return treeStructure;
              },
              (first, second) -> {
                first.putAll(second);
                return first;
              });
    };
  }

  public static <T> T rootOf(Map<T, List<T>> treeStructure) {
    var descendants = treeStructure.values().stream().flatMap(List::stream).toList();
    var nodes = new HashSet<>(treeStructure.keySet());
    nodes.removeAll(descendants);

    return nodes.iterator().next();
  }
}
