package com.github.d4span.domain.treemodel

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.choice
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.uuid
import io.kotest.property.checkAll
// import io.kotest.property.Arb.Companion.list

class TreeModelSpec : StringSpec({
    "A TreeModel should not be empty." {
        shouldThrow<IllegalArgumentException> {
            TreeModel(emptyMap<Any, List<Any>>())
        }
    }
    "A TreeModel should represent a valid tree structure." {
        checkAll(treeNodes(nodeGens, 10)) { nodes ->
            val structure = builTreeStructure(nodes)
            TreeModel(structure)
        }
    }
})

val nodeGens: List<Arb<Any>> = listOf(Arb.int(), Arb.string(), Arb.uuid())

fun <T> builTreeStructure(nodes: List<T>): Map<T, List<T>> =
    nodes.fold(emptyMap()) { map, node ->
        // If map is not empty, pick an existing node to be the parent of the new node
        if (map.isNotEmpty()) {
            val parent = map.keys.random()
            val children = map[parent] ?: emptyList()
            map + (parent to (children + node))
        } else {
            map
        } + (node to emptyList())
    }

fun <T> treeNodes(
    arbs: List<Arb<T>>,
    maxSize: Int,
): Arb<List<T>> = Arb.list(Arb.choice(arbs), 1..maxSize)
