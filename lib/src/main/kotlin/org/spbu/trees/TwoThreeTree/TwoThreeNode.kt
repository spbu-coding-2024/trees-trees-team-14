package org.spbu.trees.TwoThreeTree

import org.spbu.trees.common.Node
import kotlin.collections.mutableListOf

class TwoThreeNode<K : Comparable<K>, V> (
    override var keys: MutableList<K> = mutableListOf(),
    override var values: MutableList<V> = mutableListOf(),
    override var children: MutableList<TwoThreeNode<K, V>> = mutableListOf(),
    override var isLeaf: Boolean = true
) : Node<K, V>
