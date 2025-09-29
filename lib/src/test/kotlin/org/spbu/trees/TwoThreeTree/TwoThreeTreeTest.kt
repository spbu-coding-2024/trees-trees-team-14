package org.spbu.trees.TwoThreeTree

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertFailsWith

class TwoThreeTreeTest {

    // search function

    @Test
    fun testSearchExistingKeys() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")

        assertEquals("ten", tree.search(10))
        assertEquals("twenty", tree.search(20))
        assertEquals("thirty", tree.search(30))
    }

    @Test
    fun testSearchNonExistingKey() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")

        assertFailsWith<IllegalArgumentException> {
            tree.search(30)
        }
    }

    // contains function

    @Test
    fun testContainsExistingKeys() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")
        tree.insert(40, "forty")
        tree.insert(50, "fifty")

        assertTrue(tree.contains(10))
        assertTrue(tree.contains(30))
        assertTrue(tree.contains(50))
    }

    @Test
    fun testContainsNonExistingKeys() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(50, "fifty")
        tree.insert(60, "sixty")

        assertFalse(tree.contains(10))
        assertFalse(tree.contains(70))
    }

    // insert function

    @Test
    fun testInsertSingleElement() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(10, "ten")

        assertTrue(tree.search(10) == "ten")
        assertEquals(listOf(10), tree.root.keys)
    }

    @Test
    fun testInsertManyElementsWithSplit() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(10, "ten")
        tree.insert(20, "twenty")
        tree.insert(30, "thirty")
        tree.insert(40, "forty")

        assertTrue(tree.root.keys.size in 1..2)

        assertEquals("ten", tree.search(10))
        assertEquals("twenty", tree.search(20))
        assertEquals("thirty", tree.search(30))
        assertEquals("forty", tree.search(40))
    }

    @Test
    fun testInsertTooManyElementsStayBalanced() {
        val tree = TwoThreeTree<Int, String>()
        for (i in 1..100) {
            tree.insert(i, "$i")
        }

        // все ключи доступны
        for (i in 1..100) {
            assertEquals("$i", tree.search(i))
        }

        val leafDepths = mutableSetOf<Int>()
        fun checkDepth(node: TwoThreeNode<Int, String>, depth: Int) {
            if (node.isLeaf) {
                leafDepths.add(depth)
            } else {
                node.children.forEach { checkDepth(it, depth + 1) }
            }
        }
        checkDepth(tree.root, 0)

        assertEquals(1, leafDepths.size)
    }

    // delete function

    @Test
    fun testDeleteSingleElement() {
        val tree = TwoThreeTree<Int, String>()
        tree.insert(20, "twenty")

        tree.delete(20)

        assertFalse(tree.contains(20))
    }

    @Test
    fun testDeleteLeafElement() {
        val tree = TwoThreeTree<Int, String>()
        (1..5).forEach { tree.insert(it, "$it") }

        tree.delete(5)

        (1..4).forEach { assertTrue(tree.contains(it)) }
        assertFalse(tree.contains(5))
    }

    @Test
    fun testDeleteManyElements() {
        val tree = TwoThreeTree<Int, String>()
        (1..15).forEach { tree.insert(it, "$it") }

        listOf(3, 5, 7, 9, 11, 13).forEach { tree.delete(it) }

        listOf(3, 5, 7, 9, 11, 13).forEach { assertFalse(tree.contains(it)) }
        listOf(1, 2, 4, 6, 8, 10, 12, 14, 15).forEach { assertTrue(tree.contains(it)) }
    }

    @Test
    fun testDeleteNonExistingKey() {
        val tree = TwoThreeTree<Int, String>()
        (1..5).forEach { tree.insert(it, "$it") }

        assertFailsWith<IllegalArgumentException> {
            tree.delete(6)
        }
    }

}
