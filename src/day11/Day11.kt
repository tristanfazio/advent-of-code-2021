package day11

import Directions
import GridCell
import parseMap
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val xMax = input[0].toCharArray().size
        val yMax = input.size
        val map = Array(yMax) { Array(xMax) { 0 } }

        parseMap(map, input)

        var flashes = 0
        for (step in 0..99) {
            val flashed = mutableSetOf<GridCell>()
            val flashing: MutableSet<GridCell> = findFlashingOctopi(map)

            while (flashing.isNotEmpty()) {
                val currCell = flashing.pop()

                //flash
                flashes++
                flashed.add(currCell)

                //increment neighbours
                for (direction in Directions.values()) {
                    val neighbouringCell = getNeighbouringCell(map, currCell, direction)
                    if (neighbouringCell != null) {
                        if (flashed.none { it == neighbouringCell }) {
                            var value = map[neighbouringCell.y][neighbouringCell.x]
                            value++
                            map[neighbouringCell.y][neighbouringCell.x] = value
                            if (value > 9) {
                                flashing.add(neighbouringCell)
                            }
                        }
                    }
                }
            }

            //set flashed to 0
            flashed.forEach { gridCell ->
                map[gridCell.y][gridCell.x] = 0
            }
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val xMax = input[0].toCharArray().size
        val yMax = input.size
        val map = Array(yMax) { Array(xMax) { 0 } }
        val octopi = xMax * yMax

        parseMap(map, input)

        for (step in 0..999) {
            val flashed = mutableSetOf<GridCell>()
            val flashing: MutableSet<GridCell> = findFlashingOctopi(map)

            while (flashing.isNotEmpty()) {
                val currCell = flashing.pop()

                //flash
                flashed.add(currCell)

                //increment neighbours
                for (direction in Directions.values()) {
                    val neighbouringCell = getNeighbouringCell(map, currCell, direction)
                    if (neighbouringCell != null) {
                        if (flashed.none { it == neighbouringCell }) {
                            var value = map[neighbouringCell.y][neighbouringCell.x]
                            value++
                            map[neighbouringCell.y][neighbouringCell.x] = value
                            if (value > 9) {
                                flashing.add(neighbouringCell)
                            }
                        }
                    }
                }
            }

            //set flashed to 0
            flashed.forEach { gridCell ->
                map[gridCell.y][gridCell.x] = 0
            }
            if(flashed.size == octopi) return step + 1
        }
        return 0
    }

    val input = readInput("day11/Day11")
    val test = readInput("day11/Day11_test")

    val partOneTest = part1(test)
    println("test part1: $partOneTest")
    check(partOneTest == 1656)

    val partOneInput = part1(input)
    println("part1: $partOneInput")
    check(partOneInput == 1617)
//
    val partTwoTest = part2(test)
    println("test part2: $partTwoTest")
    check(partTwoTest == 195)
//
    val partTwoInput = part2(input)
    println("part2: $partTwoInput")
}

fun findFlashingOctopi(map: Array<Array<Int>>): MutableSet<GridCell> {
    val flashing = mutableSetOf<GridCell>()
    map.forEachIndexed { cellY, line ->
        val cells = line.map { it.toString().toInt() }
        cells.forEachIndexed { cellX, currentCellValue ->
            //gain energy
            map[cellY][cellX]++
            //flash check
            if (currentCellValue + 1 > 9) {
                flashing.add(GridCell(cellX, cellY))
            }
        }
    }
    return flashing
}

fun getNeighbouringCell(map: Array<Array<Int>>, currCell: GridCell, direction: Directions): GridCell? {
    val deltaX = currCell.x + direction.dX
    val deltaY = currCell.y + direction.dY
    if (map.getOrNull(deltaY)?.getOrNull(deltaX) != null) {
        return GridCell(deltaX, deltaY)
    }
    return null
}

fun <T> MutableCollection<T>.pop() = with(iterator()) { next().also { remove() } }