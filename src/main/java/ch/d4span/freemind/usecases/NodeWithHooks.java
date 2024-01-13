package ch.d4span.freemind.usecases;

import freemind.extensions.NodeHook;
import freemind.extensions.PermanentNodeHook;
import java.util.Collection;
import java.util.List;

public interface NodeWithHooks {

  List<PermanentNodeHook> getHooks();

  Collection<PermanentNodeHook> getActivatedHooks();

  PermanentNodeHook addHook(PermanentNodeHook hook);

  void invokeHook(NodeHook hook);

  void removeHook(PermanentNodeHook hook);

  public void removeAllHooks();
}
