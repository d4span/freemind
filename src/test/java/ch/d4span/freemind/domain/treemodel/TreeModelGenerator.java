package ch.d4span.freemind.domain.treemodel;

import static org.instancio.Instancio.ofSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.instancio.Random;
import org.instancio.generator.Generator;

@RequiredArgsConstructor
public class TreeModelGenerator<T> implements Generator<Map<T, List<T>>> {

  private final Set<Class<? extends T>> typesToGenerate;
  private final int maxSize;

  @Override
  public Map<T, List<T>> generate(Random random) {
    var size = random.intRange(1, maxSize);
    var typeToGenerate = random.oneOf(typesToGenerate);

    return ofSet(typeToGenerate).size(size).create().stream()
        .reduce(
            new HashMap<>(),
            (Map<T, List<T>> theMap, T t) -> {
              if (!theMap.isEmpty()) {
                var treeNodes = theMap.keySet().stream().toList();
                var parent = random.oneOf(treeNodes);

                theMap.get(parent).add(t);
              }

              theMap.put(t, new ArrayList<>());
              return theMap;
            },
            (Map<T, List<T>> first, Map<T, List<T>> second) -> {
              first.putAll(second);
              return first;
            });
  }
}
