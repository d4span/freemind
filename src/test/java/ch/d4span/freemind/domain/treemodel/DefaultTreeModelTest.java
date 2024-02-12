package ch.d4span.freemind.domain.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
class DefaultTreeModelTest implements TreeModelTest {

  @DisplayName("Tests the initialization of the tree model with the supplied tree structure.")
  @Test
  void testInitialization() {
    var treeStructure = TreeModelTest.treeStructure();

    var treeModel = new DefaultTreeModel<>(treeStructure.tree());
    assertThat(treeModel.treeStructure())
        .isEqualTo(treeStructure.tree())
        .isNotSameAs(treeStructure.tree());

    assertThrows(
        Exception.class,
        () -> {
          treeModel.treeStructure().clear();
        });
  }

  @DisplayName("Tests that the tree model raises an error when the tree structure is not valid.")
  @RepeatedTest(1000)
  void testInitializationInvalidTree() {
    var validTreeStructure = TreeModelTest.treeStructure();
    Map<Object, List<Object>> invalidTree = null;
    try {
      invalidTree = makeInvalid(validTreeStructure.tree());
    } catch (Exception e) {
      System.out.println(e);
    }
    var tree = invalidTree;
    assertThrows(
        Exception.class,
        () -> {
          new DefaultTreeModel<>(tree);
        });
  }

  private Map<Object, List<Object>> makeInvalid(Map<Object, List<Object>> original) {
    var map = new HashMap<>(original);
    var root = rootOf(map);

    if (map.size() == 1) {
      // There is one node, so it is removed.
      map.remove(removalCandidate(map));
    } else if (map.size() == 2) {
      if (flipCoin()) {
        var theOnlyLeafEntry =
            map.entrySet().stream().filter(e -> e.getValue().isEmpty()).findFirst().get();
        map.remove(theOnlyLeafEntry.getKey());
      } else {
        removeRandomChildFromRandomNode(map);
      }
    } else if (flipCoin()) {
      var removalCandidate = removalCandidate(map);
      // If we want to remove the tree root...
      if (removalCandidate.equals(root)) {
        // ... and the root has exactly one descendant, ...
        if (map.get(root).size() == 1) {
          // ... then we must remove another node because removing the root would leave the tree in
          // a valid state.
          removalCandidate = map.keySet().stream().filter(k -> !k.equals(root)).findFirst().get();
        }
      }
      map.remove(removalCandidate);
    } else {
      removeRandomChildFromRandomNode(map);
    }
    return map;
  }

  private Boolean flipCoin() {
    return create(Boolean.class);
  }

  private Object rootOf(HashMap<Object, List<Object>> map) {
    var nodes = new HashSet<>(map.keySet());
    var descendants =
        map.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(Collectors.toSet());
    nodes.removeAll(descendants);
    return nodes.iterator().next();
  }

  private Object removalCandidate(HashMap<Object, List<Object>> map) {
    return map.keySet().iterator().next();
  }

  private void removeRandomChildFromRandomNode(HashMap<Object, List<Object>> map) {
    var children = map.values().stream().filter(l -> !l.isEmpty()).findFirst().get();
    var childToRemove = children.iterator().next();
    children.remove(childToRemove);
  }

  @Override
  public <T> TreeModel<T> createTreeModel(Map<T, List<T>> treeStructure) {
    return new DefaultTreeModel<T>(treeStructure);
  }
}
