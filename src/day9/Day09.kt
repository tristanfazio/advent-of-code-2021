package day9

import readInput
import java.util.*


fun main() {
    fun part1(input: List<String>): Int {
        val xMax = input[0].toCharArray().size
        val yMax = input.size
        val map = Array(yMax) { Array(xMax) { 0 } }

        parseMap(map, input)
        val lowPoints = findLowPoints(map)

        return lowPoints.size + lowPoints.sumOf { it.cellValue }
    }

    fun part2(input: List<String>): Int {
        val xMax = input[0].toCharArray().size
        val yMax = input.size
        val map = Array(yMax) { Array(xMax) { 0 } }

        parseMap(map, input)
        val lowPoints = findLowPoints(map)
        val basinSizes = mutableListOf<Int>()
        lowPoints.forEach { lowPoint ->
            val basin = floodFill(map, lowPoint)
            basinSizes.add(basin.size)
        }
        basinSizes.sortDescending()

        return basinSizes.subList(0, 3).reduce { acc, i -> acc * i }
    }

    val input = readInput("day9/Day09")
    val test = readInput("day9/Day09_test")

    val partOneTest = part1(test)
    println("test part1: $partOneTest")
    check(partOneTest == 15)

    val partOneInput = part1(input)
    println("part1: $partOneInput")
    check(partOneInput == 504)

    val partTwoTest = part2(test)
    println("test part2: $partTwoTest")
    check(partTwoTest == 1134)

    val partTwoInput = part2(input)
    println("part2: $partTwoInput")
}

fun parseMap(map: Array<Array<Int>>, input: List<String>) {
    input.forEachIndexed { y, row ->
        val cells = row.map { it.toString().toInt() }
        cells.forEachIndexed { x, cell ->
            map[y][x] = cell
        }
    }
}

fun canCheckUp(tileY: Int, yMin: Int): Boolean {
    return tileY - 1 >= yMin
}

fun canCheckDown(tileY: Int, yMax: Int): Boolean {
    return tileY + 1 <= yMax

}

fun canCheckLeft(tileX: Int, xMin: Int): Boolean {
    return tileX - 1 >= xMin

}

fun canCheckRight(tileX: Int, xMax: Int): Boolean {
    return tileX + 1 <= xMax
}

fun findLowPoints(map: Array<Array<Int>>): MutableList<Cell> {
    val lowPoints = mutableListOf<Cell>()
    val yMax = map.size - 1
    val xMax = map[0].size - 1
    map.forEachIndexed { cellY, line ->
        val cells = line.map { it.toString().toInt() }
        cells.forEachIndexed { cellX, cellValue ->
            //check for low point
            var potentialLowers = 0
            var actualLowers = 0
            if (canCheckUp(cellY, 0)) {
                potentialLowers++
                if (cellValue < map[cellY - 1][cellX]) {
                    actualLowers++
                }
            }
            if (canCheckDown(cellY, yMax)) {
                potentialLowers++
                if (cellValue < map[cellY + 1][cellX]) {
                    actualLowers++
                }
            }
            if (canCheckLeft(cellX, 0)) {
                potentialLowers++
                if (cellValue < map[cellY][cellX - 1]) {
                    actualLowers++
                }
            }
            if (canCheckRight(cellX, xMax)) {
                potentialLowers++
                if (cellValue < map[cellY][cellX + 1]) {
                    actualLowers++
                }
            }
            if (potentialLowers == actualLowers) {
                lowPoints.add(Cell(cellX, cellY, cellValue))
            }
        }
    }
    return lowPoints
}


fun floodFill(map: Array<Array<Int>>, lowPoint: Cell): MutableList<Cell> {
    val queue: Queue<Cell> = LinkedList()
    val basin = mutableListOf<Cell>()
    val yMax = map.size - 1
    val xMax = map[0].size - 1
    queue.add(lowPoint)
    basin.add(lowPoint)

    while (queue.size > 0) {
        // Dequeue the front node
        val currCell: Cell = queue.remove()
        val cellX = currCell.x
        val cellY = currCell.y

        if (canCheckUp(cellY, 0)) {
            val cell = Cell(cellX, cellY - 1, map[cellY - 1][cellX])
            if(cell.cellValue < 9 && basin.none { it == cell }) {
                queue.add(cell)
                basin.add(cell)
            }
        }
        if (canCheckDown(cellY, yMax)) {
            val cell = Cell(cellX, cellY + 1, map[cellY + 1][cellX])
            if(cell.cellValue < 9 && basin.none { it == cell }) {
                queue.add(cell)
                basin.add(cell)
            }
        }
        if (canCheckLeft(cellX, 0)) {
            val cell = Cell(cellX - 1, cellY, map[cellY][cellX - 1])
            if(cell.cellValue < 9 && basin.none { it == cell }) {
                queue.add(cell)
                basin.add(cell)
            }
        }
        if (canCheckRight(cellX, xMax)) {
            val cell = Cell(cellX + 1, cellY, map[cellY][cellX + 1])
            if(cell.cellValue < 9 && basin.none { it == cell }) {
                queue.add(cell)
                basin.add(cell)
            }
        }
    }

    return basin
}

data class Cell(val x: Int, val y: Int, val cellValue: Int)
