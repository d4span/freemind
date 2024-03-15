package com.github.d4span.treemodel;

import static java.util.stream.Stream.generate;
import static org.instancio.Instancio.create;
import static org.instancio.Instancio.ofList;
import static org.instancio.quickcheck.api.artbitrary.Arbitrary.fromStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.instancio.quickcheck.api.artbitrary.Arbitrary;

public record ArbitraryTreeStructureSupplier<N>(Class<N> nodeType, int maxSize)
    implements Supplier<Arbitrary<Map<N, List<N>>>> {

  @Override
  public Arbitrary<Map<N, List<N>>> get() {
    return fromStream(generate(() -> createTreeStructure(this.nodeType, this.maxSize)));
  }

  public static <N> Map<N, List<N>> createTreeStructure(Class<N> nodeType, int maxSize) {
    var size = (create(Integer.class) % maxSize) + 1;
    return ofList(nodeType).size(size).create().stream()
        .reduce(
            new HashMap<N, List<N>>(),
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
