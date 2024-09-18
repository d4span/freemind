package org.freemind.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultTreeModelGenerators {

  public static <T> TreeModel<T> createTreeModel(Class<T> theClass, int size) {
    var treeStructure = createTreeStructure(theClass, size);

    return DefaultTreeModelGenerators.convertToTreeModel(treeStructure);
  }

  private static <T> Map<T, List<T>> createTreeStructure(Class<T> theClass, int size) {
    return Instancio.ofSet(theClass).size(size).create().stream()
        .<Map<T, List<T>>>reduce(
            new HashMap<T, List<T>>(),
            (map, t) -> {
              if (!map.isEmpty()) {
                var parent = Instancio.gen().oneOf(map.keySet()).get();
                var children = map.get(parent);
                children.add(t);
                map.put(parent, children);
              }

              map.put(t, new ArrayList<>());
              return map;
            },
            (left, right) -> {
              left.putAll(right);
              return left;
            });
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
    var rootNode = nodeMap.values().stream().filter(node -> node.parent == null).findFirst().get();

    return new DefaultTreeModel<>(rootNode);
  }
}
