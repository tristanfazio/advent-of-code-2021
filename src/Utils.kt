import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()
fun readInputAsString(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun convertBinaryToDecimal(num: Long): Int {
    var num = num
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

data class GridCell(val x: Int, val y: Int)

fun parseMap(map: Array<Array<Int>>, input: List<String>) {
    input.forEachIndexed { y, row ->
        val cells = row.map { it.toString().toInt() }
        cells.forEachIndexed { x, cell ->
            map[y][x] = cell
        }
    }
}

enum class Directions(val dX: Int, val dY: Int) {
    NORTHWEST(-1, -1),
    NORTH(0, -1),
    NORTHEAST(1, -1),
    WEST(-1, 0),
    EAST(1, 0),
    SOUTHWEST(-1, 1),
    SOUTH(0, 1),
    SOUTHEAST(1, 1)
}