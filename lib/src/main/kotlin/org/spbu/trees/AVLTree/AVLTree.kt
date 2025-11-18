package org.spbu.trees.AVLTree
import java.util.Stack
import java.lang.Integer.max

class AVLNode<T : Comparable<T>, V>(var key: T, var value: V) {
    var left: AVLNode<T, V>? = null
    var right: AVLNode<T, V>? = null
    var height: Int = 1
}
abstract class AVLTreeMain<T : Comparable<T>, V> {
     var root: AVLNode<T, V>? = null


    abstract fun insert(key: T, value: V)
    abstract fun delete(key: T)


    fun search(key: T): String {
        var current = root
        while (current != null) {
            when {
                key < current.key -> current = current.left
                key > current.key -> current = current.right
                else -> return "${current.value} (key=$key) in tree!"
            }
        }
        return "$key not in tree!"
    }

    fun keys(): List<T> {
        if (root == null) return emptyList()
        var current = root
        var temp: AVLNode<T, V>
        val arrayOfKeys = mutableListOf<T>()
        val nodes = Stack<AVLNode<T, V>>()
        while (!nodes.empty() || current != null) {
            while (current != null) {
                nodes.push(current)
                current = current.left
            }
            temp = nodes.pop()
            arrayOfKeys.add(temp.key)
            current = temp.right
        }
        return arrayOfKeys
    }
    fun keyIterator(): Iterator<T> = object : Iterator<T> {
        private val stack = Stack<AVLNode<T, V>>()
        private var current = root

        init {
            var node = current
            while (node != null) {
                stack.push(node)
                node = node.left
            }
        }

        override fun hasNext(): Boolean = !stack.isEmpty()

        override fun next(): T {
            if (!hasNext()) java.util.NoSuchElementException()


            val node = stack.pop()
            val key = node.key


            var temp = node.right
            while (temp != null) {
                stack.push(temp)
                temp = temp.left
            }

            return key
        }
    }
    fun valueIterator(): Iterator<V> = object : Iterator<V> {
        private val stack = Stack<AVLNode<T, V>>()
        private var current = root

        init {

            var node = current
            while (node != null) {
                stack.push(node)
                node = node.left
            }
        }

        override fun hasNext(): Boolean = !stack.isEmpty()

        override fun next(): V {
            if (!hasNext()) java.util.NoSuchElementException()


            val node = stack.pop()
            val value = node.value


            var temp = node.right
            while (temp != null) {
                stack.push(temp)
                temp = temp.left
            }

            return value
        }
    }
    fun pairsIterator(): Iterator<Pair<T,V>> = object : Iterator<Pair<T,V>> {
        private val stack = Stack<AVLNode<T, V>>()
        private var current = root

        init {
            // Идём до самого левого узла и кладём путь в стек
            var node = current
            while (node != null) {
                stack.push(node)
                node = node.left
            }
        }

        override fun hasNext(): Boolean = !stack.isEmpty()

        override fun next(): Pair<T,V> {
            if (!hasNext()) java.util.NoSuchElementException()

            // Берём узел из стека — это следующий по in-order
            val node = stack.pop()
            val key = node.key
            val value =node.value
            // Если у узла есть правое поддерево — проваливаемся в его самый левый узел
            var temp = node.right
            while (temp != null) {
                stack.push(temp)
                temp = temp.left
            }

            return key to value
        }
    }
    fun values(): List<V> {
        if (root == null) return emptyList()
        var current = root
        var temp: AVLNode<T, V>
        val arrayOfValues = mutableListOf<V>()
        val nodes = Stack<AVLNode<T, V>>()
        while (!nodes.empty() || current != null) {
            while (current != null) {
                nodes.push(current)
                current = current.left
            }
            temp = nodes.pop()
            arrayOfValues.add(temp.value)
            current = temp.right
        }
        return arrayOfValues
    }

    fun pairs(): List<Pair<T, V>> {
        if (root == null) return emptyList()
        var current = root
        var temp: AVLNode<T, V>
        val arrayOfPairs = mutableListOf<Pair<T, V>>()
        val nodes = Stack<AVLNode<T, V>>()
        while (!nodes.empty() || current != null) {
            while (current != null) {
                nodes.push(current)
                current = current.left
            }
            temp = nodes.pop()
            arrayOfPairs.add(temp.key to temp.value)
            current = temp.right
        }
        return arrayOfPairs
    }

    fun searchMinKey(): T? = minValue(root)?.key
    fun searchMinValue(): V? = minValue(root)?.value
    fun searchMaxKey(): T? = maxValue(root)?.key
    fun searchMaxValue(): V? = maxValue(root)?.value

    fun minValue(node: AVLNode<T, V>?): AVLNode<T, V>? {
        var current = node
        while (current?.left != null) {
            current = current.left
        }
        return current
    }

    fun maxValue(node: AVLNode<T, V>?): AVLNode<T, V>? {
        var current = node
        while (current?.right != null) {
            current = current.right
        }
        return current
    }

    fun height(node: AVLNode<T, V>?): Int {
        return node?.height ?: 0
    }

    fun balanceFactor(node: AVLNode<T, V>?): Int {
        if (node == null) return 0
        return height(node.left) - height(node.right)
    }
}
class AVLTree<T : Comparable<T>, V> : AVLTreeMain<T, V>() {

    override fun insert(key: T, value: V) {
        root = insert(root, key, value)
    }

     fun insert(node: AVLNode<T, V>?, key: T, value: V): AVLNode<T, V>? {
        if (node == null) {
            return AVLNode(key, value)
        }

        if (key < node.key) {
            node.left = insert(node.left, key, value)
        } else if (key > node.key) {
            node.right = insert(node.right, key, value)
        } else {
            node.value = value
            return node
        }

        node.height = max(height(node.left), height(node.right)) + 1

        val balance = balanceFactor(node)


        if (balance > 1) {
            if (key < node.left!!.key) {
                return rotateRight(node)
            }
            if (key > node.left!!.key) {
                return rotateLeftRight(node)
            }
        }

        if (balance < -1) {
            if (key > node.right!!.key) {
                return rotateLeft(node)
            }
            if (key < node.right!!.key) {
                return rotateRightLeft(node)
            }
        }

        return node
    }

    override fun delete(key: T) {
        root = delete(root, key)
    }

     fun delete(node: AVLNode<T, V>?, key: T): AVLNode<T, V>? {

        if (node == null) return null

        if (key < node.key) {
            node.left = delete(node.left, key)
        } else if (key > node.key) {
            node.right = delete(node.right, key)
        } else {

            if (node.left == null && node.right == null) {
                return null
            } else if (node.left != null && node.right == null) {
                return node.left
            } else if (node.left == null && node.right != null) {
                return node.right
            } else {
                val minNode = minValue(node.right)
                node.key = minNode!!.key
                node.value = minNode.value
                node.right = delete(node.right, minNode.key)
            }
        }


        node.height = max(height(node.left), height(node.right)) + 1
        val balance = balanceFactor(node)

        if (balance > 1) {
            if (balanceFactor(node.left) >= 0) {
                return rotateRight(node)
            }
            return rotateLeftRight(node)
        }

        if (balance < -1) {
            if (balanceFactor(node.right) <= 0) {
                return rotateLeft(node)
            }
            return rotateRightLeft(node)
        }

        return node
    }


    fun rotateRight(node: AVLNode<T, V>?): AVLNode<T, V>? {
        val newRoot = node?.left
        val temp = newRoot?.right

        newRoot?.right = node
        node?.left = temp

        node?.height = max(height(node?.left), height(node?.right)) + 1
        newRoot?.height = max(height(newRoot?.left), height(newRoot?.right)) + 1

        return newRoot
    }

    fun rotateLeft(node: AVLNode<T, V>?): AVLNode<T, V>? {
        val newRoot = node?.right
        val temp = newRoot?.left

        newRoot?.left = node
        node?.right = temp

        node?.height = max(height(node?.left), height(node?.right)) + 1
        newRoot?.height = max(height(newRoot?.left), height(newRoot?.right)) + 1

        return newRoot
    }

    fun rotateLeftRight(node: AVLNode<T, V>?): AVLNode<T, V>? {
        node?.left = rotateLeft(node?.left)
        return rotateRight(node)
    }

    fun rotateRightLeft(node: AVLNode<T, V>?): AVLNode<T, V>? {
        node?.right = rotateRight(node?.right)
        return rotateLeft(node)
    }
}