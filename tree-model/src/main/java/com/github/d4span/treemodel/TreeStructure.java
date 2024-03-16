package com.github.d4span.treemodel;

import java.util.List;
import java.util.Map;

public sealed class TreeStructure<N> permits ModifyableTreeStructure {
  public TreeStructure(Map<N, List<N>> treeStructure) {}

  Map<N, List<N>> structure() {
    return Map.of();
  }
}
