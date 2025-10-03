package org.spbu.trees.BSTree

import org.spbu.trees.common.Node

class BSNode<K : Comparable<K>, V>(
    val key: K,
    val value: V,
    var left: BSNode<K, V>? = null,
    var right: BSNode<K, V>? = null
) : Node<K, V> {

    override val keys: List<K>
        get() = listOf(key)

    override val values: List<V>
        get() = listOf(value)

    override val children: List<Node<K, V>>
        get() = listOfNotNull(left, right)

    override val isLeaf: Boolean
        get() = left == null && right == null
}
