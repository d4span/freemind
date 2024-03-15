package com.github.d4span.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.instancio.quickcheck.api.ForAll;
import org.instancio.quickcheck.api.Property;
import org.instancio.quickcheck.api.artbitrary.Arbitrary;
import org.junit.jupiter.api.DisplayName;

public class TreeStructureProperties {

  @DisplayName("Test that each person has valid age")
  @Property
  public void initalization(@ForAll("treeStructures") Map<Object, List<Object>> treeStructure) {
    var tree = new TreeStructure(treeStructure);
    assertThat(tree.structure()).isEqualTo(treeStructure);
  }

  public Arbitrary<Map<Object, List<Object>>> treeStructures() {
    List<Class> nodeTypes =
        List.of(Instant.class, String.class, Integer.class, Double.class, UUID.class);
    var nodeType = nodeTypes.get(create(Integer.class) % nodeTypes.size());
    return new ArbitraryTreeStructureSupplier<Object>(nodeType, 32).get();
  }
}
