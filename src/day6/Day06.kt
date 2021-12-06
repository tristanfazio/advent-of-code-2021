package day6

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val numberOfDays = 80
        return calculateSchoolGrowth(numberOfDays, input)
    }

    fun part2(input: List<String>): Long {
        val numberOfDays = 256
        return calculateSchoolGrowth(numberOfDays, input)
    }

    val input = readInput("day6/Day06")
    println(part1(input))
    println(part2(input))
}

fun calculateSchoolGrowth(numberOfDays: Int, input: List<String>): Long {
    //map of <DAY,FISH COUNT>
    val dayMap = mutableMapOf<Int, Long>()
    for (i in 0..8) {
        dayMap[i] = 0L
    }

    //get initial fish with days and add to each day of cycle
    input[0].split(",").forEach { daysToSpawn ->
        dayMap[daysToSpawn.toInt()] = dayMap[daysToSpawn.toInt()]?.plus(1)!!
    }

    //loop every day
    for (i in 0 until numberOfDays) {
        val fishToSpawn = dayMap[0] ?: 0
        dayMap[0] = dayMap[1]!!
        dayMap[1] = dayMap[2]!!
        dayMap[2] = dayMap[3]!!
        dayMap[3] = dayMap[4]!!
        dayMap[4] = dayMap[5]!!
        dayMap[5] = dayMap[6]!!
        dayMap[6] = dayMap[7]!! + fishToSpawn
        dayMap[7] = dayMap[8]!!
        dayMap[8] = fishToSpawn
    }
    return dayMap.values.sum()
}