package com.example.sudoku.Model

import android.util.Log

class SudokuSolver(inputBoard: ArrayList<Int>) {

    private var inputBoard : ArrayList<Int> = inputBoard
    private var board : Array<Array<Int>> = makeSquare()
    private var isSolved : Boolean = false
    private var outputBoard : ArrayList<Int> = arrayListOf()

    private fun makeSquare() : Array<Array<Int>> {
        val square = Array(9) { _ -> Array(9) { _ -> 0 } }
        var rowCounter = 0
        var justStarted = true
        for (i in 0..80) {
            if (i % 9 == 0) {
                when (justStarted) {
                    true  -> justStarted = false
                    false -> rowCounter += 1
                }
            }
            square[rowCounter][i%9] = inputBoard[i]
        }
        return square
    }

    private fun makeFlat() {
        var rowCounter = 0
        var justStarted = true
        for (i in 0..80) {
            if (i % 9 == 0) {
                when (justStarted) {
                    true  -> justStarted = false
                    false -> rowCounter += 1
                }
            }
            outputBoard.add(board[rowCounter][i%9])
        }
    }

    private fun inRow(row: Int, number: Int) : Boolean {
        if (board[row].indexOf(number) != -1) {
            return true
        }
        return false
    }

    private fun inCol(col: Int, number: Int) : Boolean {
        // rows change col dont, i = row , start at 0, go until board.size
        for (element in board) {
            if (element[col] == number) {
                return true
            }
        }
        return false
    }

    private fun inSubBoard(row: Int, col: Int, number: Int) : Boolean {
        val startRow = row - (row % 3)
        val startCol = col - (col % 3)

        for (i in startRow until startRow+3) {
            for (j in startCol until startCol+3) {
                if (board[i][j] == number) {
                    return true
                }
            }
        }
        return false
    }

    private fun isOkayToPlace(row: Int, col: Int, number: Int) : Boolean = (!inRow(row,number)
            && ! inCol(col, number) && !inSubBoard(row, col, number))

    private fun solvePuzzle () : Boolean {
        isSolved = true
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (board [i][j] == 0) {
                    for (k in 1..9) {
                        if (isOkayToPlace(i,j,k)) {
                            board [i][j] = k

                            if (solvePuzzle()) {
                                return true
                            } else {
                                board [i][j] = 0
                            }
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    fun getSolved() : ArrayList<Int> {
        if (!isSolved) {
            solvePuzzle()
            makeFlat()
        }
        return outputBoard
    }
}