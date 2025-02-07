import org.example.CellState
import org.example.Game
import org.junit.jupiter.api.Assertions.*
import kotlin.test.*

class GameTest {

    @Test
    fun testInitialBoardIsEmpty() {
        val game = Game()
        for (row in 0..5) {
            for (col in 0..6) {
                assertEquals(CellState.EMPTY, game.getBoardCell(row, col))
            }
        }
    }

    @Test
    fun testMakeValidMove() {
        val game = Game()
        assertTrue(game.makeMove(3))
        assertEquals(CellState.PLAYER_X, game.getBoardCell(5, 3))
    }

    @Test
    fun testMakeInvalidMove() {
        val game = Game()
        assertFalse(game.makeMove(7))
        assertFalse(game.makeMove(-1))
    }

    @Test
    fun testMakeMoveInFullColumn() {
        val game = Game()
        repeat(6) { game.makeMove(0) }
        assertFalse(game.makeMove(0))
    }

    @Test
    fun testSwitchPlayer() {
        val game = Game()
        assertEquals(CellState.PLAYER_X, game.getCurrentPlayer())
        game.switchPlayer()
        assertEquals(CellState.PLAYER_O, game.getCurrentPlayer())
        game.switchPlayer()
        assertEquals(CellState.PLAYER_X, game.getCurrentPlayer())
    }

    @Test
    fun testCheckWinHorizontal() {
        val game = Game()
        repeat(4) { col ->
            game.makeMove(col)
            if (col < 3) game.makeMove(col)
        }
        assertTrue(game.checkWin())
    }

    @Test
    fun testCheckWinVertical() {
        val game = Game()
        repeat(4) {
            game.makeMove(0)
            if (it < 3) game.makeMove(1)
        }
        assertTrue(game.checkWin())
    }

    @Test
    fun testCheckWinDiagonalAscending() {
        val game = Game()
        game.makeMove(0); game.makeMove(1)
        game.makeMove(1); game.makeMove(2)
        game.makeMove(2); game.makeMove(3)
        game.makeMove(2); game.makeMove(3)
        game.makeMove(3); game.makeMove(0)
        game.makeMove(3)
        assertTrue(game.checkWin())
    }

    @Test
    fun testCheckWinDiagonalDescending() {
        val game = Game()
        game.makeMove(3); game.makeMove(2)
        game.makeMove(2); game.makeMove(1)
        game.makeMove(1); game.makeMove(0)
        game.makeMove(1); game.makeMove(0)
        game.makeMove(0); game.makeMove(3)
        game.makeMove(0)
        assertTrue(game.checkWin())
    }

    @Test
    fun testNoWinYet() {
        val game = Game()
        game.makeMove(0); game.makeMove(1)
        game.makeMove(2); game.makeMove(3)
        assertFalse(game.checkWin())
    }

    @Test
    fun testIsBoardFullWhenFull() {
        val game = Game()
        for (col in 0..6) {
            repeat(6) { game.makeMove(col) }
        }
        assertTrue(game.isBoardFull())
    }

    @Test
    fun testIsBoardFullWhenNotFull() {
        val game = Game()
        game.makeMove(0); game.makeMove(1)
        assertFalse(game.isBoardFull())
    }
}


