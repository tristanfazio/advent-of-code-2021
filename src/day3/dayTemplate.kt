package day3

import convertBinaryToDecimal
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val arraysize = input[0].length
        val zeros = IntArray(arraysize);
        val ones = IntArray(arraysize)

        //for each line
        input.forEach {
            //line to chars
            val numbers = it.map { it.toString().toInt() }
            //for each char
            for (i in numbers.indices) {
                val value = numbers[i]
                when (value) {
                    0 -> zeros[i]++
                    1 -> ones[i]++
                }
            }
        }

        var gammaStr = ""
        var epsilonStr = ""
        for (i in 0 until arraysize) {
            if (zeros[i] > ones[i]) {
                gammaStr += "0"
                epsilonStr += "1"
            } else {
                gammaStr += "1"
                epsilonStr += "0"
            }
        }

        var gammaDecimal = convertBinaryToDecimal(gammaStr.toLong())
        var epsilonDecimal = convertBinaryToDecimal(epsilonStr.toLong())
        return gammaDecimal * epsilonDecimal
    }

    fun part2(input: List<String>): Int {
        var filteredOxygenList = input.toList()
        var index = 0
        do {
            val arraysize = input[0].length
            val zeros = IntArray(arraysize)
            val ones = IntArray(arraysize)

            //for each line
            filteredOxygenList.forEach {
                //line to chars
                val numbers = it.map { it.toString().toInt() }
                //for each char
                for (i in numbers.indices) {
                    val value = numbers[i]
                    when (value) {
                        0 -> zeros[i]++
                        1 -> ones[i]++
                    }
                }
            }

            var maxBit = if (zeros[index] > ones[index]) 0 else 1
            if(zeros[index] == ones[index]) maxBit = 1
            filteredOxygenList = filteredOxygenList.filter { number ->
                number[index].toString().toInt() == maxBit
            }
            index++
        } while (filteredOxygenList.size > 1 && index < arraysize)
        var oxygen = convertBinaryToDecimal(filteredOxygenList[0].toLong())

        var filteredCO2List = input.toList()
        index = 0
        do {
            val arraysize = input[0].length
            val zeros = IntArray(arraysize);
            val ones = IntArray(arraysize)

            //for each line
            filteredCO2List.forEach {
                //line to chars
                val numbers = it.map { it.toString().toInt() }
                //for each char
                for (i in numbers.indices) {
                    val value = numbers[i]
                    when (value) {
                        0 -> zeros[i]++
                        1 -> ones[i]++
                    }
                }
            }

            var minBit = if (zeros[index] < ones[index]) 0 else 1
            if(zeros[index] == ones[index]) minBit = 0
            filteredCO2List = filteredCO2List.filter { number ->
                number[index].toString().toInt() == minBit
            }
            index++
        } while (filteredCO2List.size > 1 && index < arraysize)
        var co2 = convertBinaryToDecimal(filteredCO2List[0].toLong())

        return co2 * oxygen
    }

    val testInput = readInput("day3/day03_test")
    val input = readInput("day3/day03")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    println("p1 test: ${part1(testInput)}")
    println("p2 test: ${part2(testInput)}")
    println("p1: ${part1(input)}")
    println("p2: ${part2(input)}")
}
