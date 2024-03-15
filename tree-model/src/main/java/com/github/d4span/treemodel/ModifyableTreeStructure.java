package com.github.d4span.treemodel;

import java.util.List;
import java.util.Map;

public final class ModifyableTreeStructure<N> extends TreeStructure<N> {

  public ModifyableTreeStructure(Map<N, List<N>> treeStructure) {
    super(treeStructure);
  }

  @Override
  Map<N, List<N>> structure() {
    throw new UnsupportedOperationException("Unimplemented method 'structure'");
  }
}
