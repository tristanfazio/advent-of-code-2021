package day4

import readInputAsString

fun main() {
    val input = readInputAsString("day4/Day04")
    val testInput = readInputAsString("day4/Day04_test")

    val part1test = part1(testInput)
    val part2test = part2(testInput)

    println(part1test)
    check(part1test == 4512)
    println(part2test)
    check(part2test == 1924)

    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int {
    val bingoGame = parseBingoInput(input)
    bingoGame.numberList.forEach { number ->
        bingoGame.bingoBoards.onEach { board -> board.check(number) }
            .firstOrNull { bingoBoard -> bingoBoard.isWinner() }?.let { winningBoard ->
                return number * winningBoard.getScore()
            }
    }
    return 0
}

fun part2(input: String): Int {
    val bingoGame = parseBingoInput(input)
    val winningBoardsOrder = mutableListOf<BingoBoard>()
    bingoGame.numberList.forEach { number ->
        bingoGame.bingoBoards.onEach { board ->
            board.check(number)
            if(board.isWinner() && !winningBoardsOrder.contains(board)) {
                winningBoardsOrder.add(board)
                if(winningBoardsOrder.size == bingoGame.bingoBoards.size) {
                    return winningBoardsOrder.last().getScore() * number
                }
            }
        }
    }
    return 0
}

fun parseBingoInput(input: String): BingoGame {
    val bingoGame = BingoGame()
    var splitInput = input.split("\n\n")
    bingoGame.numberList = splitInput[0].split(",").map { it.toInt() }.toTypedArray()
    splitInput = splitInput.drop(1)

    splitInput.forEach {
        val bingoBoard = BingoBoard(it)
        bingoGame.bingoBoards.add(bingoBoard)
    }

    return bingoGame
}

class BingoGame {
    var numberList = emptyArray<Int>()
    var bingoBoards = mutableListOf<BingoBoard>()
}

class BingoBoard(boardString: String) {
    private var board = Array(5) { Array(5) { BingoSquare(0) } }

    init {
        boardString.split("\n").forEachIndexed { rowIndex, row ->
            row.split(" ").filterNot { it.isBlank() }.forEachIndexed { columnIndex, value ->
                board[rowIndex][columnIndex] = BingoSquare(value.toInt())
            }
        }
    }

    fun check(number: Int) {
        board.forEach { row ->
            row.forEach { bingoSquare ->
                if (bingoSquare.value == number) bingoSquare.checked = true
            }
        }
    }

    fun isWinner(): Boolean {
        val didRowWin = board.any { row ->
            row.all { bingoSquare -> bingoSquare.checked }
        }

        val didColumnWin = (0 until 5).any { column ->
            board.map { row -> row[column] }.all { bingoSquare -> bingoSquare.checked }
        }

        return didRowWin || didColumnWin
    }

    fun getScore(): Int {
        val score = board.sumOf { row ->
            row.filter { bingoSquare -> !bingoSquare.checked }.sumOf { bingoSquare -> bingoSquare.value }
        }
        return score
    }
}

class BingoSquare(val value: Int) {
    var checked = false
}
