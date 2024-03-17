package com.github.d4span.domain.treemodel

data class TreeModel<N>(val structure: Map<N, List<N>>) {
    init {
        require(structure.isNotEmpty()) { "A TreeModel should not be empty." }
    }
}
