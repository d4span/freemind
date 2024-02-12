package ch.d4span.freemind.domain.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
public interface TreeModelTest {
  @DisplayName("The root of the tree model should be the same as the root of the tree structure")
  @RepeatedTest(10)
  default void testRoot() {
    var treeStructure = treeStructure();

    var treeModel = createTreeModel(treeStructure.tree());
    assertThat(treeModel.root().value()).isEqualTo(treeStructure.root());
  }

  //   @Test
  //   default void getPathToRoot() {
  //     var treeModel = createTreeModel();
  //     var node = createTreeNode();
  //     var[] path = treeModel.getPathToRoot(node);
  //     // Add assertions for path correctness
  //   }

  //   @Test
  //   default void getIndexOfChild() {
  //     var treeModel = createTreeModel();
  //     var parent = createTreeNode();
  //     var child = createTreeNode();
  //     int index = treeModel.getIndexOfChild(parent, child);
  //     // Add assertions for index correctness
  //   }

  //   @Test
  //   default void getChild() {
  //     var treeModel = createTreeModel();
  //     var parent = createTreeNode();
  //     int index = 0; // Specify the index of the child
  //     var child = treeModel.getChild(parent, index);
  //     // Add assertions for child correctness
  //   }

  //   @Test
  //   default void getChildCount() {
  //     var treeModel = createTreeModel();
  //     var parent = createTreeNode();
  //     int childCount = treeModel.getChildCount(parent);
  //     // Add assertions for child count correctness
  //   }

  //   @Test
  //   default void isLeaf() {
  //     var treeModel = createTreeModel();
  //     var node = createTreeNode();
  //     boolean isLeaf = treeModel.isLeaf(node);
  //     // Add assertions for leaf status correctness
  //   }

  // Helper methods for creating instances of TreeModel and TreeNode
  <T> TreeModel<T> createTreeModel(Map<T, List<T>> treeStructure);

  record TreeStructure(Map<Object, List<Object>> tree) {
    public Object root() {
      var descendants = tree.values().stream().flatMap(List::stream).collect(Collectors.toSet());
      var nodes = new HashSet<>(tree.keySet());
      nodes.removeAll(descendants);

      return List.copyOf(nodes).get(0);
    }
  }

  Set<Class<?>> typesToGenerate =
      Set.of(
          String.class,
          Integer.class,
          Long.class,
          Double.class,
          Float.class,
          UUID.class,
          BigDecimal.class,
          BigInteger.class);

  public static TreeStructure treeStructure() {
    return Instancio.of(TreeStructure.class)
        .generate(field(TreeStructure::tree), new TreeModelGenerator<Object>(typesToGenerate, 10))
        .create();
  }
}
