package org.spbu.trees.BSTree

class BSTree<K : Comparable<K>, V>(var root: BSNode<K, V>? = null) {

    fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }
    private fun insert(parent: BSNode<K, V>?, key: K, value: V) : BSNode<K, V> {
        if (parent == null) {
            return BSNode<K, V>(key, value)
        } else if (key < parent.key) {
            parent.left = insert(parent.left, key, value)
        } else if (key > parent.key) {
            parent.right = insert(parent.right, key, value)
        } else {
            throw IllegalArgumentException()
        }
        return parent
    }

    fun getMax() : BSNode<K, V> {
        return getMax(root)
    }
    private fun getMax(parent: BSNode<K, V>?) : BSNode<K, V> {
        if (parent == null) {
            throw IllegalArgumentException()
        } else if (parent.right == null) {
            return parent
        } else {
            return getMax(parent.right)
        }
    }

    fun delete(key: K) {
        root = delete(root, key)
    }
    private fun delete(parent: BSNode<K, V>?, key: K) : BSNode<K, V>? {
        if (parent == null) {
            throw IllegalArgumentException()
        } else if (key < parent.key) {
            parent.left = delete(parent.left, key)
        } else if (key > parent.key) {
            parent.right = delete(parent.right, key)
        } else if (parent.left == null) {
            return parent.right
        } else if (parent.right == null) {
            return parent.left
        } else {
            val maxInLeft = getMax(parent.left)
            maxInLeft.left = delete(parent.left, maxInLeft.key)
            maxInLeft.right = parent.right
            return maxInLeft
        }
        return parent
    }

    fun contains(key: K) : Boolean {
        return contains(root, key)
    }
    private fun contains(parent: BSNode<K, V>?, key: K) : Boolean {
        if (parent == null)
            return false
        else if (key < parent.key)
            return contains(parent.left, key)
        else if (key > parent.key)
            return contains(parent.right, key)
        else
            return true
    }

    fun find(key: K) : V {
        return find(root, key)
    }
    private fun find(parent: BSNode<K, V>?, key: K) : V {
        if (parent == null) {
            throw IllegalArgumentException()
        } else if (key < parent.key) {
            return find(parent.left, key)
        } else if ( key > parent.key) {
            return find(parent.right, key)
        } else {
            return parent.value
        }
    }

    fun inOrderTraversal() {
        inOrderTraversal(root)
    }
    private fun inOrderTraversal(parent: BSNode<K, V>?) {
        if(parent == null)
            return
        else {
            inOrderTraversal(parent.left)
            println("key: ${parent.key}, value: ${parent.value}")
            inOrderTraversal(parent.right)
        }
    }
}
