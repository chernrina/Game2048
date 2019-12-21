package com.example.game2048

import com.example.game2048.game.GameField
import com.example.game2048.game.GameLogic
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val RIGHT = 1
    private val LEFT = 2
    private val UP =3
    private val DOWN = 4
    @Test
    fun shiftLine() {
        var field = GameField(4)
        field.setLine(0, arrayOf(2,2,2,2))
        field.setLine(1, arrayOf(0,0,2,2))
        field.setLine(2, arrayOf(2,0,2,4))
        field.setLine(3, arrayOf(2,2,4,2))
        val game = GameLogic(field)
        game.updateState(RIGHT)
        assertEquals(field.getLine(0), arrayOf(0,0,4,4))
        assertEquals(field.getLine(1), arrayOf(0,0,0,4))
        assertEquals(field.getLine(2), arrayOf(0,0,4,4))
        assertEquals(field.getLine(3), arrayOf(0,4,4,2))
        game.updateState(UP)
        assertEquals(field.getLine(0), arrayOf(0,4,8,8))
        assertEquals(field.getLine(1), arrayOf(0,0,4,4))
        assertEquals(field.getLine(2), arrayOf(0,0,0,2))
        assertEquals(field.getLine(3), arrayOf(0,0,0,0))
        game.updateState(LEFT)
        assertEquals(field.getLine(0), arrayOf(4,16,0,0))
        assertEquals(field.getLine(1), arrayOf(8,0,0,0))
        assertEquals(field.getLine(2), arrayOf(2,0,0,0))
        assertEquals(field.getLine(3), arrayOf(0,0,0,0))
        game.updateState(DOWN)
        assertEquals(field.getLine(0), arrayOf(0,0,0,0))
        assertEquals(field.getLine(1), arrayOf(4,0,0,0))
        assertEquals(field.getLine(2), arrayOf(8,0,0,0))
        assertEquals(field.getLine(3), arrayOf(2,16,0,0))
    }
}
