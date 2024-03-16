package com.github.d4span.treemodel;

import static org.instancio.Instancio.create;
import static org.instancio.Instancio.ofList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record TreeStructureSupplier<N>(Class<Object> nodeType, int maxSize)
    implements Supplier<Map<Object, List<Object>>> {

  @Override
  public Map<Object, List<Object>> get() {
    return createTreeStructure(this.nodeType, this.maxSize);
  }

  public static <N> Map<Object, List<Object>> createTreeStructure(
      Class<Object> nodeType, int maxSize) {
    var size = (create(Integer.class) % maxSize) + 1;
    return ofList(nodeType).size(size).create().stream()
        .reduce(
            new HashMap<Object, List<Object>>(),
            (map, n) -> {
              var keys = map.keySet().stream().toList();
              if (!keys.isEmpty()) {
                var key = keys.get(create(Integer.class) % keys.size());
                map.get(key).add(n);
              }

              map.put(n, new ArrayList<>());
              return map;
            },
            (first, second) -> {
              first.putAll(second);
              return first;
            });
  }
}
