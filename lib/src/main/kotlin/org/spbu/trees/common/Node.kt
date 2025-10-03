package org.spbu.trees.common

interface Node<K : Comparable<K>, V> {

    val keys: List<K>

    val values: List<V>

    val children: List<Node<K, V>>

    val isLeaf: Boolean
}
