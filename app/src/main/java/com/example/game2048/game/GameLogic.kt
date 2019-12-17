package com.example.game2048.game

import com.example.game2048.game.GameField

class GameLogic(state: GameField) {

    private val RIGHT = 1
    private val LEFT = 2
    private val UP =3
    private val DOWN = 4
    private val field = state
    private val sizeField = state.field.size
    private var score = 0
    private var win = false

    fun getScore(): Int {
        return score
    }


    fun updateState(move: Int) : Boolean {
        var ind = sizeField -1
        win=false
        when(move) {
            RIGHT -> {
                while(ind >= 0) {
                    var line = field.getLine(ind)
                    line = newLine(line,RIGHT)
                    field.setLine(ind,line)
                    ind--
                }

            }
            LEFT -> {
                while(ind >=0) {
                    var line = field.getLine(ind)
                    line = newLine(line, LEFT)
                    field.setLine(ind,line)
                    ind--
                }
            }
            UP -> {
                while(ind >=0) {
                    var column = field.getColumn(ind)
                    column = newLine(column,UP)
                    field.setColumn(ind,column)
                    ind--
                }
            }
            DOWN -> {
                while(ind >= 0) {
                    var column = field.getColumn(ind)
                    column = newLine(column,DOWN)
                    field.setColumn(ind,column)
                    ind--
                }
            }
        }
        return win
    }


    private fun lineWithoutZeros(line: Array<Int>): Array<Int> {
        var q = 0
        var i =0
        val newLine = Array(sizeField) {0}
        while (i < sizeField) {
            if (line[i] != 0){
                newLine[q] = line[i]
                q++
            }
            i++
        }
        return newLine
    }

    private fun convertLine(line: Array<Int>): Array<Int> {
        val newLine = Array(sizeField) {0}
        var i = sizeField-1
        var j= 0
        while (line[i] == 0 && i !=0) {
            newLine[j] = line[i]
            i--
            j++
        }
        if (i >= 0) {
            i = 0
            while (j < sizeField) {
                newLine[j] = line[i]
                i++
                j++
            }
        }
        return newLine
    }

    private fun shiftLine(line: Array<Int>, side: Int) : Array<Int>{
        var i = sizeField-1
        if (side == 1) {
            while (i != 0) {
                line[i] = line[i - 1]
                i--
            }
        } else {
            i= 0
            while (i < sizeField-1) {
                line[i] = line[i +1]
                i++
            }
        }
        line[i] = 0
        return line
    }

    var numOfwin = 0
    private fun newLine(line: Array<Int>, dir:Int): Array<Int> {
        if (dir == RIGHT || dir == DOWN) {
            var newLine = lineWithoutZeros(line)
            newLine = convertLine(newLine)
            var i = sizeField - 1
            while (i != 0) {
                if (newLine[i] == newLine[i - 1]) {
                    line[i] = newLine[i] * 2
                    score += line[i]
                    if (line[i]==2048 && numOfwin == 0) {
                        win=true
                        numOfwin++
                    }
                    i--
                    newLine = shiftLine(newLine, 1)
                } else {
                    line[i] = newLine[i]
                    i--
                }
            }
            line[i]=newLine[i]
        } else {
            var newLine = lineWithoutZeros(line)
            var i = 0
            while (i <sizeField -1) {
                if (newLine[i]==newLine[i+1]) {
                    line[i] = newLine[i]*2
                    score+=line[i]
                    if (line[i]==2048 && numOfwin == 0) {
                        win=true
                        numOfwin++
                    }
                    i++
                    newLine= shiftLine(newLine,0)
                } else {
                    line[i] = newLine[i]
                    i++
                }
            }
            line[i]=newLine[i]
        }
        return line
    }
}
