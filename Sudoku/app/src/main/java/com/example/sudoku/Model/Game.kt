package com.example.sudoku.Model

import androidx.lifecycle.MutableLiveData

class Game {
    var selectedCellLiveData = MutableLiveData<Pair<Int,Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()

    private var selectedRow = -1
    private var selectedCol = -1

    private lateinit var board: Board

    init{
        selectedCellLiveData.postValue(Pair(selectedRow,selectedCol))
    }

    fun handleInput (number: Int) {
        if (selectedRow == -1 || selectedCol == -1) {
            return
        }
        if (board.getCell(selectedRow,selectedCol).isStartingCell){
            return
        }

        board.getCell(selectedRow,selectedCol).value = number
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row:Int, col:Int) {
        if (!board.getCell(row, col).isStartingCell) {
            selectedRow = row
            selectedCol = col
            selectedCellLiveData.postValue(Pair(row, col))
        }
    }

    fun setStartingCells (expect: ArrayList<Int>) {
        val cells = List(9*9) {i -> Cell(i/9, i % 9 , 0) }
        for (i in 0..80) {
            val expectedVal = expect[i]
            if (expectedVal > 0) {// if start[i] > 0 change cells[i].value = start[i]
                cells[i].isStartingCell = true
                cells[i].value = expectedVal
            }
            //else move along
        }
        board = Board(9,cells)
        cellsLiveData.postValue(board.cells)
    }

    fun erase() {
        val cell = board.getCell(selectedRow,selectedCol)
        cell.value = 0
        cellsLiveData.postValue(board.cells)
    }

    fun wonTheGame(solved: ArrayList<Int>) : Boolean {
        for (i in 0 until 81) {
            if (board.cells[i].value == 0){
                return false
            }

            if (board.cells[i].value != solved[i]) {
                return false
            }
        }
        return true
    }
}