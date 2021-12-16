package day8

import readInput

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
                if (digits.contains(digit.length)) count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val digitMap = mutableMapOf<Int, Set<Char>>()
            //split the line
            val signalSets = line.split(" | ")[0].split(" ").map { it.toSet() }
            val outputSets = line.split(" | ")[1].split(" ").map { it.toSet() }
            //for each signal in the line
            digitMap[1] = signalSets.find(2)
            digitMap[4] = signalSets.find(4)
            digitMap[7] = signalSets.find(3)
            digitMap[8] = signalSets.find(7)
            digitMap[9] = signalSets.find(6) { it.containsAll(digitMap[4]!!) }
            digitMap[0] = signalSets.find(6) { it.containsAll(digitMap[7]!!) && !it.containsAll(digitMap[4]!!) }
            digitMap[6] = signalSets.find(6) { it != digitMap[9] && it != digitMap[0] }
            digitMap[3] = signalSets.find(5) { it.containsAll(digitMap[1]!!) }
            digitMap[5] = signalSets.find(5) { digitMap[6]?.containsAll(it)!! }
            digitMap[2] = signalSets.find(5) { it != digitMap[3] && it != digitMap[5] }

            val numberList = mutableListOf<Int>()
            outputSets.forEach { digitSet ->
                val digit = digitMap.filterValues { it == digitSet }.keys.single()
                numberList.add(digit)
            }
            sum += numberList.joinToString("").toInt()
        }
        return sum
    }

    val input = readInput("day8/Day08")
    val test = readInput("day8/Day08_test")
    println("test part1: ${part1(test)}")
    println("test part2: ${part2(test)}")

    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

fun List<Set<Char>>.find(size: Int, predicate: (Set<Char>) -> Boolean = { true }): Set<Char> {
    return filter { it.size == size }.single { predicate(it) }
}