package day7

import readInput
import kotlin.math.sign

const val ONE = 2
const val FOUR = 4
const val SEVEN = 3
const val EIGTH = 7
val digits = arrayOf(ONE, FOUR, SEVEN, EIGTH)

fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach { line ->
            val output = line.split(" | ")[1]
            output.split(" ").forEach { digit ->
                if(digits.contains(digit.length)) count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var digitToCharsMap = mutableMapOf<Int,CharArray>()
        input.forEach { line ->
            val signals = line.split(" | ")[0]
            signals.split(" ").forEach { signal ->
                println(signal.toCharArray())
            }
        }
        return 0
    }

    val input = readInput("day8/Day08")
    val test = readInput("day8/Day08_test")
//    println("test part1: ${part1(test)}")
    println("test part2: ${part2(test)}")

    val test1 = arrayOf("a", "b")
    val test2 = arrayOf("b", "a")
    val test3 = arrayOf("c", "a")
    print(test1.contentEquals(test2))
    print(test1.contentEquals(test3))
//    println("part1: ${part1(input)}")
//    println("part2: ${part2(input)}")
}
