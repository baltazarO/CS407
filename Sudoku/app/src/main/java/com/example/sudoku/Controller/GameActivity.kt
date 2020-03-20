package com.example.sudoku.Controller

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sudoku.Model.Cell
import com.example.sudoku.Model.SudokuSolver
import com.example.sudoku.R
import com.example.sudoku.View.SudokuBoardView
import kotlinx.android.synthetic.main.game_page.*
import java.net.HttpURLConnection
import java.net.URL


class GameActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener{

    private lateinit var viewModel: SudokuController

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_page)

        val url= intent.getStringExtra("reqString")
        val jsonBoard = AsyncTaskJsonHandler().execute(url)
        val startingBoard = stringToBoard(jsonBoard.get())
        val sudokuSolver : SudokuSolver = SudokuSolver(startingBoard)
        val finishedBoard = sudokuSolver.getSolved()

        sudokuView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(SudokuController::class.java)
        viewModel.sudokuGame.setStartingCells(startingBoard)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })

        val buttons = listOf(oneButton, twoButton, threeButton, fourButton, fiveButton,
            sixButton, sevenButton, eightButton, nineButton)

        val tStart = System.currentTimeMillis()

        buttons.forEachIndexed{ index,button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
                if (viewModel.sudokuGame.wonTheGame(finishedBoard)){
                    val tEnd = System.currentTimeMillis()
                    val tDelta = tEnd - tStart
                    val elapsedSeconds = tDelta / 1000.0
                    manageScores(elapsedSeconds)
                }
            }
        }

        val removeButton = findViewById<Button>(R.id.erase)
        removeButton.setOnClickListener{
            viewModel.sudokuGame.erase()
        }

        val seeRules = findViewById<Button>(R.id.rules)
        seeRules.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.rules_of_sudoku))
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun updateCells(cells: List<Cell>?) = cells.let {
        sudokuView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell:Pair<Int,Int>?) = cell?.let {
        sudokuView.updateSelectedCellUI(cell.first,cell.second)
    }

    override fun onCellTouch(row:Int, col:Int){
        viewModel.sudokuGame.updateSelectedCell(row,col)

    }

    private fun stringToBoard (string: String) : ArrayList<Int> {
        var s = string
        s = s.removePrefix("{\"board\":")
        s = s.replace("}", "")
        s = s.drop(1)
        s = s.dropLast(1)
        val oneDee = ArrayList<Int>()
        for (i in 0..8) {
            val re = s.substring(0..18)
            s = s.drop(20)
            oneDee.addAll(re.removeSurrounding("[", "]").split(",").map { it.toInt() })
        }
        return oneDee
    }

    private fun manageScores(newTime: Double) {
        val builder = AlertDialog.Builder(this)
        val sharedPref = this?.getSharedPreferences("high_scores", Context.MODE_PRIVATE)
        val playerScore = sharedPref.getString("player","100000000.2")?.toDouble()
        if (playerScore == null || newTime < playerScore) {
            with(sharedPref.edit()) {
                putString("key", "value")
                putString("player", newTime.toString())
                commit()
            }
            builder.setMessage("You won the game!\nYour new time is: $newTime seconds")
        } else {
            builder.setMessage("You won the game!\nYour time is: $playerScore seconds")
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    inner class AsyncTaskJsonHandler: AsyncTask<String, String, String>(){
        override fun doInBackground(vararg params: String?): String {
            var string : String
            val connection = URL(params[0]).openConnection() as HttpURLConnection //make url and make connection
            try { //
                connection.connect()
                string = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }
            finally {
                connection.disconnect()
            }
            return string
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

    }

    private fun handleJson(jsonString: String?){
        // do nothing
    }
}