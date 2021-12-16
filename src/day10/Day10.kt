package day10

import readInput
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.ceil

private val closeToOpenMap: HashMap<String, String> = hashMapOf(
    ")" to "(",
    "}" to "{",
    "]" to "[",
    ">" to "<"
)

private val openToCloseMap: HashMap<String, String> = hashMapOf(
    "(" to ")",
    "{" to "}",
    "[" to "]",
    "<" to ">"
)

private val closeToPointsMap: HashMap<String, Int> = hashMapOf(
    ")" to 3,
    "}" to 57,
    "]" to 1197,
    ">" to 25137
)

private val closeToPointsMap2: HashMap<String, Int> = hashMapOf(
    ")" to 1,
    "]" to 2,
    "}" to 3,
    ">" to 4
)

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val corruptedChar = getCorruptedCharacter(line.chunked(1))
            sum += closeToPointsMap.getOrDefault(corruptedChar, 0)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val droppedList = input.toMutableList()
        val closingSequences = mutableListOf<String>()

        //parse out corrupted lines
        droppedList.removeAll { line ->
            getCorruptedCharacter(line.chunked(1)) != null
        }

        //construct incomplete sequences
        droppedList.forEach { line ->
            closingSequences.add(getIncompleteSequence(line.chunked(1)))
        }

        //calculate scores
        val scores = closingSequences.map { sequence ->
            var sequenceScore = 0L
            sequence.forEach {
                sequenceScore *= 5
                sequenceScore += closeToPointsMap2[it.toString()]!!
            }
            sequenceScore
        }.sorted()

        //sort scores
        return scores[ceil((scores.size / 2).toDouble()).toInt()]
    }

    val input = readInput("day10/Day10")
    val test = readInput("day10/Day10_test")

    val partOneTest = part1(test)
    println("test part1: $partOneTest")
    check(partOneTest == 26397)

    val partOneInput = part1(input)
    println("part1: $partOneInput")
    check(partOneInput == 168417)

    val partTwoTest = part2(test)
    println("test part2: $partTwoTest")
    check(partTwoTest == 288957L)

    val partTwoInput = part2(input)
    println("part2: $partTwoInput")
}

fun getCorruptedCharacter(line: List<String>): String? {
    val stack: Stack<String> = Stack()
    line.forEach { char ->
        when (char) {
            "(", "[", "{", "<" -> stack.push(char)
            ")", "]", "}", ">" -> {
                if (stack.peek() != closeToOpenMap[char]) {
                    return char
                } else {
                    stack.pop()
                }
            }
        }
    }
    return null
}

fun getIncompleteSequence(line: List<String>): String {
    val stack: Stack<String> = Stack()
    val closingSequence = mutableListOf<String>()
    line.forEach { char ->
        when (char) {
            "(", "[", "{", "<" -> stack.push(char)
            ")", "]", "}", ">" -> {
                if (stack.peek() == closeToOpenMap[char]) {
                    stack.pop()
                }
            }
        }
    }
    while (stack.size > 0) {
        val openingChar = stack.pop()
        openToCloseMap[openingChar]?.let { closingSequence.add(it) }
    }
    return closingSequence.joinToString("")
}

