package ch.d4span.freemind.domain.treemodel;

import static org.instancio.Instancio.create;
import static org.instancio.Instancio.ofList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum TreeStructureGenerator {
  ;

  public static <T> Function<Integer, Map<T, List<T>>> forElementType(Class<T> elementType) {
    return (Integer size) ->
        ofList(elementType).size(size).create().stream()
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
  }
}
