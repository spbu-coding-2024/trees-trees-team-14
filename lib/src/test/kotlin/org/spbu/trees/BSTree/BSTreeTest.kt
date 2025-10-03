package org.spbu.trees.BSTree

import org.junit.Assert
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.spbu.trees.TwoThreeTree.TwoThreeTree
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BSTreeTest {

    // insert function

    @Test
    fun testInsertRoot() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        assertNotNull(tree.root)
        assertEquals(10, tree.root?.key)
        assertEquals("ten", tree.root?.value)
    }

    @Test
    fun testInsertLeftAndRight() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(0, "zero")
        tree.insert(20, "twenty")

        assertNotNull(tree.root)
        assertNotNull(tree.root?.left)
        assertEquals(0, tree.root?.left?.key)
        assertEquals("zero", tree.root?.left?.value)

        assertNotNull(tree.root?.right)
        assertEquals(20, tree.root?.right?.key)
        assertEquals("twenty", tree.root?.right?.value)
    }

    @Test
    fun testInsertDuplicateKeysThrowsException() {
        assertFailsWith<IllegalArgumentException> {
            val tree = BSTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(10, "ten")
        }
    }

    // contains function

    @Test
    fun testContainsOnEmptyTree() {
        val tree = BSTree<Int, String>()
        assertFalse(tree.contains(10))
    }

    @Test
    fun testContainsRootKey() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        assertTrue(tree.contains(10))
    }

    @Test
    fun testContainsLeftAndRightKeys() {
        val tree = BSTree<Int, String>()

        tree.insert(10, "ten")
        tree.insert(0, "zero")
        tree.insert(20, "twenty")
        tree.insert(-10, "negative ten")
        tree.insert(30, "thirty")

        assertTrue(tree.contains(0))
        assertTrue(tree.contains(20))
        assertTrue(tree.contains(-10))
        assertTrue(tree.contains(30))
    }

    @Test
    fun testContainsNonExistingKeys() {
        val tree = BSTree<Int, String>()

        tree.insert(10, "ten")
        tree.insert(0, "zero")
        tree.insert(20, "twenty")

        assertFalse(tree.contains(-1)) // less than min
        assertFalse(tree.contains(30)) // more the max
        assertFalse(tree.contains(15)) // between keys
    }

    // Search function

    @Test
    fun testSearchOnEmptyTree() {
        val tree = BSTree<Int, String>()
        assertFailsWith<IllegalArgumentException> {
            tree.search(10)
        }
    }

    @Test
    fun testSearchNotExistingKeys() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(0, "zero")
        tree.insert(20, "twenty")
        tree.insert(-10, "negative ten")
        tree.insert(30, "thirty")

        assertFailsWith<IllegalArgumentException> {

            tree.search(1)
            tree.search(-100)
            tree.search(100)
            tree.search(25)
        }
    }

    @Test
    fun testSearchLeftAndRightKeys() {
        val tree = BSTree<Int, String>()

        tree.insert(10, "ten")
        tree.insert(0, "zero")
        tree.insert(20, "twenty")
        tree.insert(-10, "negative ten")
        tree.insert(30, "thirty")

        val ten = tree.search(10)
        assertEquals("ten", ten)

        val zero = tree.search(0)
        assertEquals("zero", zero)

        val twenty = tree.search(20)
        assertEquals("twenty", twenty)

        val negativeTen = tree.search(-10)
        assertEquals("negative ten", negativeTen)

        val thirty = tree.search(30)
        assertEquals("thirty", thirty)
    }

    // getMax function

    @Test
    fun testGetMaxOnEmptyTree() {
        val tree = BSTree<Int, String>()
        assertFailsWith<IllegalArgumentException> {
            tree.getMax()
        }
    }

    @Test
    fun testGetMaxSingleNode() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        val max = tree.getMax()
        assertEquals(10, max.key)
    }

    @Test
    fun testGetMaxRightChain() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")

        val max = tree.getMax()
        assertEquals(30, max.key)
        assertEquals("thirty", max.value)
    }

    // delete function

    @Test
    fun testDeleteOnEmptyTree() {
        val tree = BSTree<Int, String>()
        assertFailsWith<IllegalArgumentException> {
            tree.delete(10)
        }
    }

    @Test
    fun testDeleteLeafNode() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")

        tree.delete(30)

        assertFalse(tree.contains(30))
        assertTrue(tree.contains(10))
        assertTrue(tree.contains(20))
    }

    @Test
    fun testDeleteNodeWithOneChild() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")

        tree.delete(10)

        assertFalse(tree.contains(10))
        assertTrue(tree.contains(20))
        assertTrue(tree.contains(30))
    }

    @Test
    fun testDeleteNodeWithTwoChildren() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(5, "five")
        tree.insert(15, "fifteen")
        tree.insert(3, "three")
        tree.insert(7, "seven")

        tree.delete(5)

        assertFalse(tree.contains(5))
        assertTrue(tree.contains(3))
        assertTrue(tree.contains(7))
        assertTrue(tree.contains(10))
    }

    @Test
    fun testDeleteNonExistingKey() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(5, "five")
        tree.insert(15, "fifteen")

        assertFailsWith<IllegalArgumentException> {
            tree.delete(100)
        }
    }

    // iterator

    @Test
    fun testEmptyTreeIteratorHasNoElements() {
        val tree = BSTree<Int, String>()
        val iter = tree.iterator()

        Assert.assertFalse(iter.hasNext())
        assertFailsWith<NoSuchElementException> {
            iter.next()
        }
    }

    @Test
    fun testIteratingSingleElementTree() {
        val tree = BSTree<Int, String>()
        tree.insert(42, "Fox")

        val iter = tree.iterator()
        Assert.assertTrue(iter.hasNext())

        val (k, v) = iter.next()
        assertEquals(42, k)
        assertEquals("Fox", v)
        Assert.assertFalse(iter.hasNext())
        assertFailsWith<NoSuchElementException> {
            iter.next()
        }
    }

    @Test
    fun testIteratorTraversesInOrder() {
        val tree = BSTree<Int, String>()
        (1..15).forEach { tree.insert(it, "$it") }

        val iter = tree.iterator()
        val keys = mutableListOf<Int>()
        while (iter.hasNext()) {
            keys.add(iter.next().first)
        }

        assertEquals((1..15).toList(), keys)
    }

    @Test
    fun testIteratorSupportHasNextSeveralTimes() {
        val tree = BSTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(11, "eleven")

        val iter = tree.iterator()
        Assert.assertTrue(iter.hasNext())
        Assert.assertTrue(iter.hasNext())

        val first = iter.next()
        assertEquals(10, first.first)

        Assert.assertTrue(iter.hasNext())
        val second = iter.next()
        assertEquals(11, second.first)

        Assert.assertFalse(iter.hasNext())
    }
}
