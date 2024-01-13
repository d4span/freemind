package freemind.modes;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import freemind.modes.mindmapmode.MindMapNodeModel;

@ExtendWith(InstancioExtension.class)
class NodeAdapterTest {

  @Test
  void testTextProperty() {
    var nodeAdapter = Instancio.create(MindMapNodeModel.class);

    Assertions.assertThat(nodeAdapter).isNotNull();

    nodeAdapter.setXmlText("<br>Hello World");
    Assertions.assertThat(nodeAdapter.getText()).isEqualTo("Hello World");
  }
}
