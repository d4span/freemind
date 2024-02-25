package ch.d4span.freemind.domain.treemodel;

import static org.assertj.core.api.Assertions.assertThat;

public interface TreeModelTest {

    TreeModel<?> getTreeModel();

    default void testRoot() {
        var treeModel = getTreeModel();
        assertThat(treeModel.root()).as("Root node should not be null").isNotNull();
    }
}
