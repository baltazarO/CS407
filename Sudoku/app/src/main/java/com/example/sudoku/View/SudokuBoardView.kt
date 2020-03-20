package com.example.sudoku.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.sudoku.Model.Cell
import kotlin.math.min

class SudokuBoardView (context: Context, attributeSet: AttributeSet): View(context,attributeSet){

    private var sqrtSize = 3
    private var size = 9
    private var selectedRow = 0
    private var selectedCol = 0
    private var cellSizePixels = 0F
    private var listener : SudokuBoardView.OnTouchListener? = null
    private var cells : List<Cell>? = null

    private val thickLine = Paint().apply {// refactor paints to a class?
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }

    private val thinLine = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    private val selectedCellColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#00ced1")
    }

    private val alignedCellColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#afeeee")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val startCellTextColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    private val startCellColor = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#c0c0c0")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePixels,sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        updateMeasurements(width)
        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    private fun updateMeasurements(width: Int){
        cellSizePixels = (width / size).toFloat()
        textPaint.textSize = cellSizePixels / 1.5F
        startCellTextColor.textSize = cellSizePixels / 1.5F
    }

    private fun fillCells(canvas: Canvas){
        cells?.forEach{
            val r = it.row
            val c = it.col

            if (it.isStartingCell) {
                fillCell(canvas, r, c, startCellColor)
            } else if (r == selectedRow && c == selectedCol) {
                fillCell(canvas, r, c, selectedCellColor)
            } else if (r == selectedRow || c == selectedCol) {
                fillCell(canvas, r, c, alignedCellColor)
            } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize) {
                fillCell(canvas, r, c, alignedCellColor)
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c:Int, paint:Paint){
        canvas.drawRect(c*cellSizePixels,r*cellSizePixels, (c+1)*cellSizePixels, (r+1)*cellSizePixels, paint)
    }

    private fun drawLines(canvas: Canvas){
        canvas.drawRect(0F,0F,width.toFloat(),height.toFloat(),thickLine)

        for(i in 1 until size){
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLine
                else -> thinLine
            }

            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach{
            val valOfCell = it.value
            if (valOfCell != 0) {
                val row = it.row
                val col = it.col
                val valueString = it.value.toString()
                val paintToUse = if (it.isStartingCell) startCellTextColor else textPaint
                val textBounds = Rect()
                paintToUse.getTextBounds(valueString, 0, valueString.length, textBounds)
                val textWidth = paintToUse.measureText(valueString)
                val textHeight = textBounds.height()

                canvas.drawText(
                    valueString, (col * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                    (row * cellSizePixels) + cellSizePixels / 2 + textHeight / 2, paintToUse
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action){
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x,event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float){
        val possibleSelectedRow = (y/cellSizePixels).toInt()
        val possibleSelectedCol = (x/cellSizePixels).toInt()
        listener?.onCellTouch(possibleSelectedRow,possibleSelectedCol)
    }

    fun updateSelectedCellUI(row:Int, col:Int){
        selectedRow = row
        selectedCol = col
        invalidate()
    }

    fun updateCells(cells: List<Cell>?){
        this.cells = cells
        invalidate()
    }

    fun registerListener(listener: SudokuBoardView.OnTouchListener) {
        this.listener = listener
    }

    interface OnTouchListener{
        fun onCellTouch(row: Int, col: Int)
    }
}