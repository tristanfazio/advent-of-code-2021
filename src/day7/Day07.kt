package day7

import readInputAsString
import kotlin.math.abs

fun main() {
    fun part1(input: String): Int {
        //Hoz to Fuel
        val fuelSpent = mutableMapOf<Int,Int>()
        val split = input.split(",")
        split.forEachIndexed { index, hozValue1 ->
            if(fuelSpent.contains(hozValue1.toInt())) return@forEachIndexed
            val mutableSplit = split.toMutableList()
            mutableSplit.removeAt(index)
            mutableSplit.forEach { hozValue2 ->
                 fuelSpent[hozValue1.toInt()] = (fuelSpent[hozValue1.toInt()] ?: 0 ) + abs(hozValue2.toInt() - hozValue1.toInt())
            }
        }
        return fuelSpent.values.toList().sortedWith(compareBy { it }).first()
    }
    fun part2(input: String): Int {
        //Hoz to Fuel
        val fuelSpent = mutableMapOf<Int,Int>()
        val split = input.split(",")
        val min = split.sortedWith(compareBy { it }).first().toInt()
        val max = split.sortedWith(compareBy { it }).last().toInt()
        for(i in min..max) {
            val mutableSplit = split.toMutableList()
            mutableSplit.forEach { hozValue2 ->
                val distance = abs(hozValue2.toInt() - i)
                fuelSpent[i] = (fuelSpent[i] ?: 0 ) + fuelSpent(distance)
            }
        }
        return fuelSpent.values.toList().sortedWith(compareBy { it }).first()
    }

    val input = readInputAsString("day7/Day07")
    val test = readInputAsString("day7/Day07_test")
    println("test part1: ${part1(test)}")
    println("test part2: ${part2(test)}")

    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}


fun fuelSpent(distance: Int): Int {
    if (distance <= 0)
        return distance;
    return fuelSpent(distance-1) + distance
}
