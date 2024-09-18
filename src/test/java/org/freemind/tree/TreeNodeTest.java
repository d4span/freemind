package org.freemind.tree;

import java.util.List;
import java.util.Optional;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

@ExtendWith(InstancioExtension.class)
class TreeNodeTest {

  @ParameterizedTest
  @InstancioSource
  @DisplayName("Tests proper handling of children")
  final <T> void testHandlingOfChildren(Class<T> typeOfElements) {
    var children =
        typeOfElements.isInterface()
            ? List.of()
            : typeOfElements.equals(Optional.class)
                ? List.of(Optional.empty())
                : List.of(Instancio.create(typeOfElements));
  }
}
