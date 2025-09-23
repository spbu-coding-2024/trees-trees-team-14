package org.spbu.trees.BSTree

class BSNode<K: Comparable<K>, V> (
    val key: K,
    val value: V,
    var right: BSNode<K, V>? = null,
    var left: BSNode<K, V>? = null
)