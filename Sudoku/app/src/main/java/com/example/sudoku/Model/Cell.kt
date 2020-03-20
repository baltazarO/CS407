package com.example.sudoku.Model

data class Cell(val row : Int, val col:Int,var value:Int, var isStartingCell: Boolean = false)