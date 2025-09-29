package org.spbu.trees.TwoThreeTree

import kotlin.collections.mutableListOf

class TwoThreeNode<K : Comparable<K>, V> (
    var keys: MutableList<K> = mutableListOf(),
    var values: MutableList<V> = mutableListOf(),
    var children: MutableList<TwoThreeNode<K, V>> = mutableListOf(),
    var isLeaf: Boolean = true
)
