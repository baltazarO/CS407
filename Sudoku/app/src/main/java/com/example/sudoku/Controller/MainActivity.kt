package com.example.sudoku.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.sudoku.R



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficulties = resources.getStringArray(R.array.Difficulty)
        val spinner = findViewById<Spinner>(R.id.spinner)
        var diffLevel : String = "https://sugoku.herokuapp.com/board?difficulty=hard"
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulties)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val theDiff = difficulties[position]
                    diffLevel = "https://sugoku.herokuapp.com/board?difficulty=${theDiff}"
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val button = findViewById<Button>(R.id.needed)
        button.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("reqString", diffLevel)
            startActivity(intent)
        }
    }
}
