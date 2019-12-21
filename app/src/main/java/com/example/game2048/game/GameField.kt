package com.example.game2048.game

class GameField(num: Int) {
    var field = Array(num) { Array(num) {0} }

    fun generateNewCell(i:Int) {
        var ind = 0
        while (ind != i) {
            val x = (field.indices).random()
            val y = (field.indices).random()
            val probability = (0 until 10).random()
            if (probability != 9) {
                if (field[x][y] == 0) {
                    field[x][y] = 2
                    ind++
                }
            } else if (field[x][y] == 0) {
                field[x][y] = 4
                ind++
            }
        }
    }

    fun getField(x: Int, y:Int): Int {
        return field[x][y]
    }

    fun setLine(i:Int, newLine: Array<Int>)  {
        field[i]=newLine
    }

    fun getLine(i:Int): Array<Int> {
        return field[i]
    }

    fun setColumn(i:Int, newColumn: Array<Int>) {
        for (ind in field.indices) {
            field[ind][i] = newColumn[ind]
        }
    }

    fun getColumn(i:Int): Array<Int> {
        val column = Array(field.size) {0}
        var ind = 0
        while (ind<field.size) {
            column[ind] = field[ind][i]
            ind++
        }
        return column
    }
}