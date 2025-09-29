package org.spbu.trees.TwoThreeTree

class TwoThreeTree<K : Comparable<K>, V>(
    var root: TwoThreeNode<K, V> = TwoThreeNode()
) {

    fun contains(key: K): Boolean {
        return contains(root, key)
    }
    private fun contains(node: TwoThreeNode<K, V>, key: K): Boolean {
        var i = 0
        while (i < node.keys.size && key > node.keys[i]) {
            i++
        }
        if (i < node.keys.size && node.keys[i] == key) {
            return true
        }
        if (node.isLeaf) {
            return false
        }
        return contains(node.children[i], key)
    }

    fun search(key: K): V {
        return search(root, key)
    }

    private fun search(node: TwoThreeNode<K, V>, key: K): V {
        var i = 0
        while (i < node.keys.size && key > node.keys[i]) {
            i++
        }
        if (i < node.keys.size && node.keys[i] == key) {
            return node.values[i]
        }
        if (node.isLeaf) {
            throw IllegalArgumentException()
        }
        return search(node.children[i], key)
    }

    fun insert(key: K, value: V) {
        if (root.keys.size == 3) {
            val newRoot = TwoThreeNode<K, V>(
                keys = mutableListOf(),
                values = mutableListOf(),
                children = mutableListOf(root),
                isLeaf = false
            )
            splitChild(newRoot, 0)
            root = newRoot
        }
        insertNonFull(root, key, value);
    }

    private fun insertNonFull(node: TwoThreeNode<K, V>, key: K, value: V) {
        if (node.isLeaf) {
            node.keys.add(key)
            node.values.add(value)
            val combined = node.keys.zip(node.values).sortedBy { it.first }
            node.keys.clear()
            node.values.clear()
            node.keys.addAll(combined.map { it.first })
            node.values.addAll(combined.map { it.second })
        } else {
            var i = node.keys.size - 1
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            val child = node.children[i]
            if (child.keys.size == 3) {
                splitChild(node, i)
                if (key > node.keys[i]) {
                    i++
                }
            }
            insertNonFull(node.children[i], key, value)
        }
    }

    private fun splitChild(parent: TwoThreeNode<K, V>, index: Int) {
        val fullChild = parent.children[index]
        val midKey = fullChild.keys[1]
        val midValue = fullChild.values[1]

        val leftNode = TwoThreeNode<K, V>(
            keys = mutableListOf(fullChild.keys[0]),
            values = mutableListOf(fullChild.values[0]),
            isLeaf = fullChild.isLeaf
        )
        val rightNode = TwoThreeNode<K, V>(
            keys = mutableListOf(fullChild.keys[2]),
            values = mutableListOf(fullChild.values[2]),
            isLeaf = fullChild.isLeaf
        )

        if (!fullChild.isLeaf) {
            leftNode.children.addAll(listOf(fullChild.children[0], fullChild.children[1]))
            rightNode.children.addAll(listOf(fullChild.children[2], fullChild.children[3]))
        }

        parent.keys.add(index, midKey)
        parent.values.add(index, midValue)

        parent.children[index] = leftNode
        parent.children.add(index+1, rightNode)
    }

    fun delete(key: K) {
        delete(root, key)
    }

    private fun delete(node: TwoThreeNode<K, V>, key: K) {
        val idx = node.keys.indexOfFirst { it >= key }

        if (idx >= 0 && idx < node.keys.size && node.keys[idx] == key) {
            if (node.isLeaf) {
                node.keys.removeAt(idx)
                node.values.removeAt(idx)
            } else {
                val leftChild = node.children[idx]
                val rightChild = node.children[idx + 1]

                if (leftChild.keys.size > 1) {
                    val predKey = getMaxKey(leftChild)
                    val predValue = getMaxValue(leftChild)
                    node.keys[idx] = predKey
                    node.values[idx] = predValue
                    delete(leftChild, predKey)
                } else if (rightChild.keys.size > 1) {
                    val succKey = getMinKey(rightChild)
                    val succValue = getMinValue(rightChild)
                    node.keys[idx] = succKey
                    node.values[idx] = succValue
                    delete(leftChild, succKey)
                } else {
                    mergeChildren(node, idx)
                    delete(leftChild, key)
                }
            }
        } else {
            if (node.isLeaf) {
                throw IllegalArgumentException()
            }

            val childIndex = if (idx >= 0) idx else node.keys.size
            var child = node.children[childIndex]

            if (child.keys.size == 1) {
                if (childIndex > 0 && node.children[childIndex - 1].keys.size > 1) {
                    borrowFromLeftSibling(node, childIndex)
                } else if (childIndex < node.children[childIndex].keys.size &&
                    node.children[childIndex + 1].keys.size > 1
                ) {
                    borrowFromRightSibling(node, childIndex)
                } else {
                    val mergeIndex = if (childIndex > 0) childIndex - 1 else childIndex
                    mergeChildren(node, mergeIndex)
                    child = node.children[mergeIndex]
                }
            }

            delete(child, key)
        }
    }

    private fun mergeChildren(parent: TwoThreeNode<K, V>, index: Int) {
        val left = parent.children[index]
        val right = parent.children[index + 1]

        val midKey = parent.keys.removeAt(index)
        val midValue = parent.values.removeAt(index)

        parent.children.removeAt(index + 1)

        left.keys.add(midKey)
        left.values.add(midValue)
        left.keys.addAll(right.keys)
        left.values.addAll(right.values)

        if (!left.isLeaf) {
            left.children.addAll(right.children)
        }
    }

    private fun borrowFromLeftSibling(parent: TwoThreeNode<K, V>, childIndex: Int) {
        val child = parent.children[childIndex]
        val leftSibling = parent.children[childIndex - 1]

        child.keys.add(0, parent.keys[childIndex - 1])
        child.values.add(0, parent.values[childIndex - 1])

        parent.keys[childIndex - 1] = leftSibling.keys.removeAt(leftSibling.keys.size - 1)
        parent.values[childIndex - 1] = leftSibling.values.removeAt(leftSibling.values.size - 1)

        if (!leftSibling.isLeaf) {
            val movedChild = leftSibling.children.removeAt(leftSibling.children.size - 1)
            child.children.add(0, movedChild)
        }
    }

    private fun borrowFromRightSibling(parent: TwoThreeNode<K, V>, childIndex: Int) {
        val child = parent.children[childIndex]
        val rightSibling = parent.children[childIndex + 1]

        child.keys.add(parent.keys[childIndex])
        child.values.add(parent.values[childIndex])

        parent.keys[childIndex] = rightSibling.keys.removeAt(0)
        parent.values[childIndex] = rightSibling.values.removeAt(0)

        if (!rightSibling.isLeaf) {
            val movedChild = rightSibling.children.removeAt(0)
            child.children.add(movedChild)
        }
    }

    fun getMaxKey(): K {
        return getMaxKey(root)
    }
    fun getMaxValue(): V {
        return getMaxValue(root)
    }
    fun getMinKey(): K {
        return getMinKey(root)
    }
    fun getMinValue(): V {
        return getMinValue(root)
    }
    private fun getMaxKey(node: TwoThreeNode<K, V>): K {
        if (node.keys.isEmpty()) {
            throw IllegalArgumentException()
        }
        return if (node.isLeaf) node.keys.last() else getMaxKey(node.children.last())
    }
    private fun getMaxValue(node: TwoThreeNode<K, V>): V {
        if (node.values.isEmpty()) {
            throw IllegalArgumentException()
        }
        return if (node.isLeaf) node.values.last() else getMaxValue(node.children.last())
    }
    private fun getMinKey(node: TwoThreeNode<K, V>): K {
        if (node.keys.isEmpty()) {
            throw IllegalArgumentException()
        }
        return if (node.isLeaf) node.keys.first() else getMaxKey(node.children.first())
    }
    private fun getMinValue(node: TwoThreeNode<K, V>): V {
        if (node.keys.isEmpty()) {
            throw IllegalArgumentException()
        }
        return if (node.isLeaf) node.values.first() else getMaxValue(node.children.first())
    }
}
