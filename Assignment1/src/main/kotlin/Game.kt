//Name: Sajjan Gautam
//ID: 2358466
package org.example

enum class CellState(val symbol: Char) {
    EMPTY(' '),
    PLAYER_X('X'),
    PLAYER_O('O');

    override fun toString(): String = symbol.toString()
}

class Game {
    private val board: Array<Array<CellState>> = Array(6) { Array(7) { CellState.EMPTY } }
    private var currentPlayer: CellState = CellState.PLAYER_X

    fun displayBoard() {
        println("\n 0 1 2 3 4 5 6")
        board.forEach { row ->
            println(row.joinToString("|", prefix = "|", postfix = "|") { it.symbol.toString() })
        }
        println()
    }


    fun makeMove(column: Int): Boolean {
        if (column !in 0..6) return false
        for (row in 5 downTo 0) {
            if (board[row][column] == CellState.EMPTY) {
                board[row][column] = currentPlayer
                return true
            }
        }
        return false
    }


    fun checkWin(): Boolean {
        fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
            val symbol = currentPlayer.symbol
            return (0..3).all {
                val r = row + it * dRow
                val c = col + it * dCol
                r in 0..5 && c in 0..6 && board[r][c].symbol == symbol
            }
        }

        for (row in 0..5) {
            for (col in 0..6) {
                if (checkDirection(row, col, 0, 1) ||
                    checkDirection(row, col, 1, 0) ||
                    checkDirection(row, col, 1, 1) ||
                    checkDirection(row, col, 1, -1)) {
                    return true
                }
            }
        }
        return false
    }


    fun isBoardFull(): Boolean = board.flatten().none { it == CellState.EMPTY }


    fun switchPlayer() {
        currentPlayer = if (currentPlayer == CellState.PLAYER_X) CellState.PLAYER_O else CellState.PLAYER_X
    }

    fun getBoardCell(row: Int, col: Int): CellState {
        return board[row][col]
    }

    fun getCurrentPlayer(): CellState {
        return currentPlayer
    }

    fun play() {
        println("Welcome to Connect 4!")
        do {
            displayBoard()
            println("Player ${currentPlayer.symbol}, enter column (0-6):")
            val input = readLine()?.toIntOrNull()

            if (input == null || !makeMove(input)) {
                println("Invalid move. Try again.")
                continue
            }

            if (checkWin()) {
                displayBoard()
                println("Player ${currentPlayer.symbol} wins!")
                break
            } else if (isBoardFull()) {
                displayBoard()
                println("It's a draw!")
                break
            }

            switchPlayer()
        } while (true)

        do {
            println("Play again? (y/n)")
            val answer = readLine()?.lowercase()
            when (answer) {
                "y" -> {
                    Game().play()
                    break
                }
                "n" -> {
                    println("Thanks for playing!")
                    break
                }
                else -> println("Invalid input. Please enter 'y' or 'n'.")
            }
        } while (true)
    }
}

