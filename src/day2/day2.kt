package day2

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var hoz = 0
        input.forEach {
            val action = it.split(" ")[0]
            val value = it.split(" ")[1].toInt()

            when (action) {
                "forward" -> hoz += value
                "up" -> depth -= value
                "down" -> depth += value
                else -> return@forEach
            }
        }
        return hoz * depth
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var hoz = 0
        var aim = 0
        input.forEach {
            val action = it.split(" ")[0]
            val value = it.split(" ")[1].toInt()

            when (action) {
                "forward" -> {
                    hoz += value
                    depth += aim * value
                }
                "up" -> aim -= value
                "down" -> aim += value
                else -> return@forEach
            }
        }
        return hoz * depth
    }

    val input = readInput("day2/Day02")
    println(part1(input))
    println(part2(input))
}
