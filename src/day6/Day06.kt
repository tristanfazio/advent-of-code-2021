package day6

import readInput

const val NUM_DAYS = 18

fun main() {
    fun part1(input: List<String>): Int {
        var initialFishList = mutableListOf<LanternFish>()
        input[0].split(",").forEach { daysToSpawn ->
            initialFishList.add(LanternFish(daysToSpawn.toInt()))
        }

        for (i in 0 until 80) {
            val newFishList = mutableListOf<LanternFish>()
            var fishToSpawn = 0
            initialFishList.forEach { fish ->
                val agingFish = fish.copy()
                if (agingFish.shouldSpawn()) {
                    fishToSpawn++
                }
                agingFish.age()
                newFishList.add(agingFish)
            }
            for (j in 0 until fishToSpawn) {
                newFishList.add(LanternFish())
            }
            initialFishList = newFishList
        }
        return initialFishList.size
    }

    fun part2(input: List<String>): Long {
        //map of <DAY,FISH COUNT>
        val dayMap = mutableMapOf(
            0 to 0L,
            1 to 0L,
            2 to 0L,
            3 to 0L,
            4 to 0L,
            5 to 0L,
            6 to 0L,
            7 to 0L,
            8 to 0L,
        )

        //get initial fish with days and add to each day of cycle
        input[0].split(",").forEach { daysToSpawn ->
            dayMap[daysToSpawn.toInt()] = dayMap[daysToSpawn.toInt()]?.plus(1)!!
        }

        //loop every day
        for (i in 0 until NUM_DAYS) {
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

    val testInput = readInput("day6/Day06_test")
    val input = readInput("day6/Day06")
    println(part1(testInput))
    println(part2(testInput))
    println(part1(input))
    println(part2(input))
}

data class LanternFish(var daysToSpawn: Int) {
    constructor() : this(8)

    fun age() {
        if (shouldSpawn()) {
            daysToSpawn = 6
        } else {
            daysToSpawn--
        }
    }

    fun shouldSpawn(): Boolean {
        return daysToSpawn == 0
    }
}