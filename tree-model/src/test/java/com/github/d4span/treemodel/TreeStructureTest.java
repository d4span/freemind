package com.github.d4span.treemodel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.of;
import static org.instancio.Select.field;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@ExtendWith(InstancioExtension.class)
public class TreeStructureTest {

  @DisplayName("TreeStructure initalization with a tree structure map should return the same map.")
  @ParameterizedTest
  @MethodSource("treeStructures")
  void testInitalization(Map<Object, List<Object>> treeStructure) {
    assertThat(new TreeStructure<>(treeStructure).structure()).isEqualTo(treeStructure);
  }

  public static Stream<Map<Object, List<Object>>> treeStructures() {
    var nodeType =
        of(NodeType.class)
            .generate(
                field(NodeType::type), gen -> gen.oneOf(UUID.class, Integer.class, String.class))
            .create();

    var argumentSupplier = new TreeStructureSupplier<>(nodeType.type, 32);
    return Stream.generate(argumentSupplier).limit(10);
  }

  private record NodeType(Class<Object> type) {}
}
