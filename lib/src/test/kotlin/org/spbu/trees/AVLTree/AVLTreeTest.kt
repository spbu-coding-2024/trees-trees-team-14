    package org.spbu.trees.AVLTree

    import org.junit.Assert.assertEquals
    import org.junit.Test
    import kotlin.collections.emptyList

    class AVLTreeTest {

        // Вспомогательная функция для преобразования числа в слово (для значений)
        private fun numberToWord(n: Int): String = when (n) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            4 -> "four"
            5 -> "five"
            6 -> "six"
            7 -> "seven"
            8 -> "eight"
            9 -> "nine"
            10 -> "ten"
            11 -> "eleven"
            12 -> "twelve"
            13 -> "thirteen"
            14 -> "fourteen"
            15 -> "fifteen"
            16 -> "sixteen"
            17 -> "seventeen"
            18 -> "eighteen"
            19 -> "nineteen"
            20 -> "twenty"
            22 -> "twenty-two"
            23 -> "twenty-three"
            27 -> "twenty-seven"
            30 -> "thirty"
            31 -> "thirty-one"
            33 -> "thirty-three"
            35 -> "thirty-five"
            39 -> "thirty-nine"
            40 -> "forty"
            45 -> "forty-five"
            50 -> "fifty"
            53 -> "fifty-three"
            55 -> "fifty-five"
            67 -> "sixty-seven"
            70 -> "seventy"
            79 -> "seventy-nine"
            86 -> "eighty-six"
            90 -> "ninety"
            91 -> "ninety-one"
            93 -> "ninety-three"
            95 -> "ninety-five"
            100 -> "hundred"
            146 -> "one hundred forty-six"
            else -> "number_$n"
        }

        // Проверка простейших случаев: С одним корнем и 4 поворотами
        @Test
        fun testRoot() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            assertEquals(10, tree.root?.key)
            assertEquals("ten", tree.root?.value)
        }

        @Test
        fun testRightRight() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(9, "nine")
            tree.insert(8, "eight")
            assertEquals(9, tree.root?.key)
            assertEquals("nine", tree.root?.value)
            assertEquals(8, tree.root?.left?.key)
            assertEquals("eight", tree.root?.left?.value)
            assertEquals(10, tree.root?.right?.key)
            assertEquals("ten", tree.root?.right?.value)
        }

        @Test
        fun testLeftLeft() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(11, "eleven")
            tree.insert(12, "twelve")
            assertEquals(11, tree.root?.key)
            assertEquals("eleven", tree.root?.value)
            assertEquals(10, tree.root?.left?.key)
            assertEquals("ten", tree.root?.left?.value)
            assertEquals(12, tree.root?.right?.key)
            assertEquals("twelve", tree.root?.right?.value)
        }

        @Test
        fun testLeftRight() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(7, "seven")
            tree.insert(9, "nine")
            assertEquals(9, tree.root?.key)
            assertEquals("nine", tree.root?.value)
            assertEquals(7, tree.root?.left?.key)
            assertEquals("seven", tree.root?.left?.value)
            assertEquals(10, tree.root?.right?.key)
            assertEquals("ten", tree.root?.right?.value)
        }

        @Test
        fun testRightLeft() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(12, "twelve")
            tree.insert(11, "eleven")
            assertEquals(11, tree.root?.key)
            assertEquals("eleven", tree.root?.value)
            assertEquals(10, tree.root?.left?.key)
            assertEquals("ten", tree.root?.left?.value)
            assertEquals(12, tree.root?.right?.key)
            assertEquals("twelve", tree.root?.right?.value)
        }

        // Вставка дубля обновляет значение (или оставляет как есть — здесь обновляем)
        @Test
        fun equalInsert() {
            val tree = AVLTree<Int, String>()
            tree.insert(10, "ten")
            tree.insert(11, "eleven")
            tree.insert(12, "twelve")
            tree.insert(12, "twelve_new") // обновление значения
            assertEquals(11, tree.root?.key)
            assertEquals("eleven", tree.root?.value)
            assertEquals(10, tree.root?.left?.key)
            assertEquals("ten", tree.root?.left?.value)
            assertEquals(12, tree.root?.right?.key)
            assertEquals("twelve_new", tree.root?.right?.value)
        }

        // Более сложные тесты на проверку поворотов при вставке
        @Test
        fun advancedInsert1() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(15, 3, 11, 5, 6, 17, 12, 19, 20)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(11, tree.root?.key)
            assertEquals(5, tree.root?.left?.key)
            assertEquals(15, tree.root?.right?.key)
            assertEquals(3, tree.root?.left?.left?.key)
            assertEquals(6, tree.root?.left?.right?.key)
            assertEquals(12, tree.root?.right?.left?.key)
            assertEquals(19, tree.root?.right?.right?.key)
            assertEquals(17, tree.root?.right?.right?.left?.key)
            assertEquals(20, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedInsert2() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(10, 5, 7, 8, 9, 3, 6, 2, 1, 11, 12)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(7, tree.root?.key)
            assertEquals(5, tree.root?.left?.key)
            assertEquals(9, tree.root?.right?.key)
            assertEquals(2, tree.root?.left?.left?.key)
            assertEquals(6, tree.root?.left?.right?.key)
            assertEquals(1, tree.root?.left?.left?.left?.key)
            assertEquals(3, tree.root?.left?.left?.right?.key)
            assertEquals(8, tree.root?.right?.left?.key)
            assertEquals(11, tree.root?.right?.right?.key)
            assertEquals(10, tree.root?.right?.right?.left?.key)
            assertEquals(12, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedInsert3() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(50, 30, 35, 31, 20, 11, 9, 23, 22, 40, 45)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(30, tree.root?.key)
            assertEquals(11, tree.root?.left?.key)
            assertEquals(35, tree.root?.right?.key)
            assertEquals(9, tree.root?.left?.left?.key)
            assertEquals(22, tree.root?.left?.right?.key)
            assertEquals(20, tree.root?.left?.right?.left?.key)
            assertEquals(23, tree.root?.left?.right?.right?.key)
            assertEquals(31, tree.root?.right?.left?.key)
            assertEquals(45, tree.root?.right?.right?.key)
            assertEquals(40, tree.root?.right?.right?.left?.key)
            assertEquals(50, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedInsertRR() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(8, tree.root?.key)
            assertEquals(4, tree.root?.left?.key)
            assertEquals(10, tree.root?.right?.key)
            assertEquals(2, tree.root?.left?.left?.key)
            assertEquals(6, tree.root?.left?.right?.key)
            assertEquals(1, tree.root?.left?.left?.left?.key)
            assertEquals(3, tree.root?.left?.left?.right?.key)
            assertEquals(5, tree.root?.left?.right?.left?.key)
            assertEquals(7, tree.root?.left?.right?.right?.key)
            assertEquals(9, tree.root?.right?.left?.key)
            assertEquals(11, tree.root?.right?.right?.key)
        }

        @Test
        fun advancedInsertLL() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(4, tree.root?.key)
            assertEquals(2, tree.root?.left?.key)
            assertEquals(8, tree.root?.right?.key)
            assertEquals(1, tree.root?.left?.left?.key)
            assertEquals(3, tree.root?.left?.right?.key)
            assertEquals(6, tree.root?.right?.left?.key)
            assertEquals(10, tree.root?.right?.right?.key)
            assertEquals(5, tree.root?.right?.left?.left?.key)
            assertEquals(7, tree.root?.right?.left?.right?.key)
            assertEquals(9, tree.root?.right?.right?.left?.key)
            assertEquals(11, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedInsertLR() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(20, 1, 19, 2, 18, 3, 17, 4, 16, 5)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(18, tree.root?.key)
            assertEquals(4, tree.root?.left?.key)
            assertEquals(19, tree.root?.right?.key)
            assertEquals(2, tree.root?.left?.left?.key)
            assertEquals(16, tree.root?.left?.right?.key)
            assertEquals(1, tree.root?.left?.left?.left?.key)
            assertEquals(3, tree.root?.left?.left?.right?.key)
            assertEquals(5, tree.root?.left?.right?.left?.key)
            assertEquals(17, tree.root?.left?.right?.right?.key)
            assertEquals(20, tree.root?.right?.right?.key)
        }

        @Test
        fun advancedInsertRL() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(1, 20, 2, 19, 3, 18, 4, 17, 5, 16)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(3, tree.root?.key)
            assertEquals(2, tree.root?.left?.key)
            assertEquals(17, tree.root?.right?.key)
            assertEquals(1, tree.root?.left?.left?.key)
            assertEquals(5, tree.root?.right?.left?.key)
            assertEquals(19, tree.root?.right?.right?.key)
            assertEquals(4, tree.root?.right?.left?.left?.key)
            assertEquals(16, tree.root?.right?.left?.right?.key)
            assertEquals(18, tree.root?.right?.right?.left?.key)
            assertEquals(20, tree.root?.right?.right?.right?.key)
        }

        // Проверки на удаление
        @Test
        fun deleteAllNodes() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(50, 30, 35, 31, 20, 11, 9, 23, 22, 40, 45)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            for (i in array) {
                tree.delete(i)
            }
            assertEquals(null, tree.root)
        }

        @Test
        fun testDeleteRR() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(10, 5, 15, 3)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(15)
            assertEquals(5, tree.root?.key)
            assertEquals("five", tree.root?.value)
            assertEquals(3, tree.root?.left?.key)
            assertEquals("three", tree.root?.left?.value)
            assertEquals(10, tree.root?.right?.key)
            assertEquals("ten", tree.root?.right?.value)
        }

        @Test
        fun testDeleteLR() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(10, 5, 15, 6)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(15)
            assertEquals(6, tree.root?.key)
            assertEquals("six", tree.root?.value)
            assertEquals(5, tree.root?.left?.key)
            assertEquals("five", tree.root?.left?.value)
            assertEquals(10, tree.root?.right?.key)
            assertEquals("ten", tree.root?.right?.value)
        }

        @Test
        fun testDeleteRL() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(10, 5, 15, 12)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(5)
            assertEquals(12, tree.root?.key)
            assertEquals("twelve", tree.root?.value)
            assertEquals(10, tree.root?.left?.key)
            assertEquals("ten", tree.root?.left?.value)
            assertEquals(15, tree.root?.right?.key)
            assertEquals("fifteen", tree.root?.right?.value)
        }

        @Test
        fun advancedDelete1() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(50, 30, 35, 31, 20, 11, 9, 23, 22, 40, 45)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(9)
            assertEquals(30, tree.root?.key)
            assertEquals(22, tree.root?.left?.key)
            assertEquals(35, tree.root?.right?.key)
            assertEquals(11, tree.root?.left?.left?.key)
            assertEquals(23, tree.root?.left?.right?.key)
            assertEquals(20, tree.root?.left?.left?.right?.key)
            assertEquals(31, tree.root?.right?.left?.key)
            assertEquals(45, tree.root?.right?.right?.key)
            assertEquals(40, tree.root?.right?.right?.left?.key)
            assertEquals(50, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedDelete2() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(10, 5, 7, 8, 9, 3, 6, 2, 1, 11, 12)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(9)
            assertEquals(7, tree.root?.key)
            assertEquals(5, tree.root?.left?.key)
            assertEquals(10, tree.root?.right?.key)
            assertEquals(2, tree.root?.left?.left?.key)
            assertEquals(1, tree.root?.left?.left?.left?.key)
            assertEquals(3, tree.root?.left?.left?.right?.key)
            assertEquals(6, tree.root?.left?.right?.key)
            assertEquals(8, tree.root?.right?.left?.key)
            assertEquals(11, tree.root?.right?.right?.key)
            assertEquals(12, tree.root?.right?.right?.right?.key)
        }

        @Test
        fun advancedDelete3() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(39, 70, 79, 33, 90, 91, 95, 31, 40, 53)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(70)
            assertEquals(79, tree.root?.key)
            assertEquals(33, tree.root?.left?.key)
            assertEquals(91, tree.root?.right?.key)
            assertEquals(31, tree.root?.left?.left?.key)
            assertEquals(40, tree.root?.left?.right?.key)
            assertEquals(39, tree.root?.left?.right?.left?.key)
            assertEquals(53, tree.root?.left?.right?.right?.key)
            assertEquals(90, tree.root?.right?.left?.key)
            assertEquals(95, tree.root?.right?.right?.key)
        }

        @Test
        fun advancedDelete4() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(15, 3, 11, 5, 6, 17, 12, 19, 20)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            tree.delete(11)
            assertEquals(12, tree.root?.key)
            assertEquals(5, tree.root?.left?.key)
            assertEquals(19, tree.root?.right?.key)
            assertEquals(3, tree.root?.left?.left?.key)
            assertEquals(6, tree.root?.left?.right?.key)
            assertEquals(15, tree.root?.right?.left?.key)
            assertEquals(17, tree.root?.right?.left?.right?.key)
            assertEquals(20, tree.root?.right?.right?.key)
        }

        @Test
        fun searchInEmptyTree() {
            val tree = AVLTree<Int, String>()
            assertEquals("10 not in tree!", tree.search(10))
        }

        @Test
        fun searchInTree() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(4, 5, 9, 10, 11, 19, 15, 16)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals("four (key=4) in tree!", tree.search(4))
            assertEquals("five (key=5) in tree!", tree.search(5))
            assertEquals("nine (key=9) in tree!", tree.search(9))
            assertEquals("ten (key=10) in tree!", tree.search(10))
            assertEquals("eleven (key=11) in tree!", tree.search(11))
            assertEquals("nineteen (key=19) in tree!", tree.search(19))
            assertEquals("fifteen (key=15) in tree!", tree.search(15))
            assertEquals("sixteen (key=16) in tree!", tree.search(16))
            assertEquals("20 not in tree!", tree.search(20))
        }

        @Test
        fun searchMaxAndMinInEmptyTree() {
            val tree = AVLTree<Int, String>()
            assertEquals(null, tree.searchMinKey())
            assertEquals(null, tree.searchMaxKey())
            assertEquals(null, tree.searchMinValue())
            assertEquals(null, tree.searchMaxValue())
        }

        @Test
        fun searchMaxMinInTreeWithOneNode() {
            val tree = AVLTree<Int, String>()
            tree.insert(1, "one")
            assertEquals(1, tree.searchMinKey())
            assertEquals("one", tree.searchMinValue())
            assertEquals(1, tree.searchMaxKey())
            assertEquals("one", tree.searchMaxValue())
        }

        @Test
        fun searchMaxAndMin() {
            val tree = AVLTree<Int, String>()
            val array = arrayOf(1, 9, 4, 93, 146, 27, 23, 55, 86, 67)
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            assertEquals(146, tree.searchMaxKey())
            assertEquals("one hundred forty-six", tree.searchMaxValue())
            assertEquals(1, tree.searchMinKey())
            assertEquals("one", tree.searchMinValue())
        }
        @Test
        fun udobniyPerebor(){
            val tree = AVLTree<Int, String>()
            val array = arrayOf(39, 70, 79, 33, 90, 91, 95, 31, 40, 53)
            val arraySorted1 = array.sorted()
            for (i in array) {
                tree.insert(i, numberToWord(i))
            }
            var i=0
            for(key in tree.keys()){
                assertEquals(arraySorted1[i], key)
                i++
            }
            i=0
            for(value in tree.values()){
                assertEquals(numberToWord(arraySorted1[i]), value)
                i++
            }
            i=0
            for((key,value) in tree.pairs()){
                assertEquals(arraySorted1[i], key)
                assertEquals(numberToWord(arraySorted1[i]), value)
                i++
            }
        }
        @Test
        fun udobniyPereborInNullTree(){
            val tree = AVLTree<Int, String>()
            assertEquals(emptyList<Int>(), tree.keys())
            assertEquals(emptyList<String>(), tree.values())
            assertEquals(emptyList<Pair<Int, String>>(), tree.pairs())
        }
    }