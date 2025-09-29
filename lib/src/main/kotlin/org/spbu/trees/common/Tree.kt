package org.spbu.trees.common

interface Tree<K : Comparable<K>, V, N : Node<K, V>> {
    var root: N?

    fun insert(key: K, value: V)

    fun delete(key: K)

    fun search(key: K): V

    fun contains(key: K): Boolean
}
