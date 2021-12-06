package day5

import readInput
import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val coordinateMap = mutableMapOf<Coordinate, Int>()

        input.forEach { line ->
            val splitLine = line.split(" -> ")
            val coordinate1 = createCoordinateFromSplit(splitLine[0])
            val coordinate2 = createCoordinateFromSplit(splitLine[1])

            //traverse if horizontal
            if (coordinate1.y == coordinate2.y) {
                val xMax = max(coordinate1.x, coordinate2.x)
                val xMin = min(coordinate1.x, coordinate2.x)
                for (i in xMin..xMax) {
                    val traversalCoordinate = Coordinate(i, coordinate1.y)
                    coordinateMap[traversalCoordinate] = coordinateMap[traversalCoordinate]?.plus(1) ?: 1
                }
            }
            //traverse if vertical
            if (coordinate1.x == coordinate2.x) {
                val yMax = max(coordinate1.y, coordinate2.y)
                val yMin = min(coordinate1.y, coordinate2.y)
                for (i in yMin..yMax) {
                    val traversalCoordinate = Coordinate(coordinate1.x, i)
                    coordinateMap[traversalCoordinate] = coordinateMap[traversalCoordinate]?.plus(1) ?: 1
                }
            }
        }

        return coordinateMap.count { coordinate ->
            coordinate.value >= 2
        }
    }


    fun part2(input: List<String>): Int {
        val coordinateMap = mutableMapOf<Coordinate, Int>()

        input.forEach { line ->
            val splitLine = line.split(" -> ")
            val coordinate1 = createCoordinateFromSplit(splitLine[0])
            val coordinate2 = createCoordinateFromSplit(splitLine[1])

            //traverse
            val steps: Int = if(abs(coordinate1.x - coordinate2.x) > 0) {
                abs(coordinate1.x - coordinate2.x)
            } else {
                abs(coordinate1.y - coordinate2.y)
            }
            for (i in 0..steps) {
                val nextx = nextStep(i, coordinate1.x, coordinate2.x)
                val nexty = nextStep(i, coordinate1.y, coordinate2.y)
                val traversalCoordinate = Coordinate(nextx, nexty)
                coordinateMap[traversalCoordinate] = coordinateMap[traversalCoordinate]?.plus(1) ?: 1
            }
        }

        return coordinateMap.count { coordinate ->
            coordinate.value >= 2
        }
    }

    val testInput = readInput("day5/Day05_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day5/Day05")
    println(part1(input))
    println(part2(input))
}

fun nextStep(step: Int, lineStart: Int, lineEnd: Int): Int {
    if (lineEnd - lineStart > 0) return lineStart + step
    if (lineEnd - lineStart < 0) return lineStart - step
    return lineStart
}

fun createCoordinateFromSplit(coordString: String): Coordinate {
    val x = coordString.split(",")[0].toInt()
    val y = coordString.split(",")[1].toInt()
    return Coordinate(x, y)
}

data class Coordinate(val x: Int, val y: Int)

//cool one liner to retrieve from map and increment, saving for later
//        coordinateMap[coord3] = (coordinateMap[coord3] ?: 0) + 1
