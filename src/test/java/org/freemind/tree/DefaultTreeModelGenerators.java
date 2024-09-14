package org.freemind.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InstancioExtension.class)
public enum DefaultTreeModelGenerators {
  ;

  public static <T> TreeModel<T> createTreeModel(Class<T> theClass, int size) {
    var treeStructure =
        Instancio.ofSet(theClass).size(size).create().stream()
            .<Map<T, List<T>>>reduce(
                new HashMap<T, List<T>>(),
                (Map<T, List<T>> map, T t) -> Map.of(),
                (Map<T, List<T>> left, Map<T, List<T>> right) -> Map.of());

    return DefaultTreeModelGenerators.convertToTreeModel(treeStructure);
  }

  public static <T> TreeModel<T> convertToTreeModel(Map<T, List<T>> treeStructure) {
    Map<T, MutableTreeNode<T>> nodeMap = new HashMap<>();

    for (var entry : treeStructure.entrySet()) {
      var parent = entry.getKey();
      var children = entry.getValue();

      var parentNode =
          nodeMap.computeIfAbsent(
              parent, node -> MutableTreeNodeImpl.<T>builder().value(node).build());

      var i = 0;
      for (T child : children) {
        var childNode =
            nodeMap.computeIfAbsent(
                child, node -> MutableTreeNodeImpl.<T>builder().value(node).build());
        parentNode.insert(childNode, i++);
      }
    }

    // Get the root node from the nodeMap, that is the only node whose parent == null.
    var rootNode =
        nodeMap.values().stream().filter(node -> node.parent == null).findFirst().get();

    return new DefaultTreeModel<>(rootNode);
  }
}
