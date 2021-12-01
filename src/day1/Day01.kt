package day1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var counter: Int = 0;
        var prev: Int;
        var next: Int;
        input.forEachIndexed() {index, element ->
            prev = element.toInt()

            val nextIndex = index + 1
            if (nextIndex > input.size - 1) return@forEachIndexed
            next = input[nextIndex].toInt()

            if(prev < next) counter++
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        var counter: Int = 0;
        input.forEachIndexed() {index, element ->
            //window 1
            val w1LeftIndex = index
            val w1MiddleIndex = index + 1
            val w1RightIndex = index + 2

            //window 2
            val w2LeftIndex = w1MiddleIndex
            val w2MiddleIndex = w1MiddleIndex + 1
            val w2RightIndex = w1MiddleIndex + 2

            //EOA guard
            val indices = listOf(w1LeftIndex, w1MiddleIndex, w1RightIndex, w2LeftIndex, w2MiddleIndex, w2RightIndex)
            if(indices.any{ it > input.size -1}) return@forEachIndexed

            val window1Sum = input[w1LeftIndex].toInt() + input[w1MiddleIndex].toInt() + input[w1RightIndex].toInt()
            val window2Sum = input[w2LeftIndex].toInt() + input[w2MiddleIndex].toInt() + input[w2RightIndex].toInt()
            if(window1Sum < window2Sum) counter++
        }
        return counter
    }

    fun part1refined(input: List<String>): Int {
        val list = input.map { it.toInt() }
        return list.windowed(2).count {(a,b) -> a < b }
    }

    fun part2refined(input: List<String>): Int {
        val list = input.map { it.toInt() }
        return list.windowed(4).count {(a,b,c,d) -> a < d }
    }

    val input = readInput("day1/Day01")
    println("part1:${part1(input)}")
    println("part2:${part2(input)}")
    println("part1refined:${part1refined(input)}")
    println("part2refined:${part2refined(input)}")
}
