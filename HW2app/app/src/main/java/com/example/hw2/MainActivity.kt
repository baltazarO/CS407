package com.example.hw2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.parseDouble
import android.view.inputmethod.InputMethodManager
import android.widget.*

//Baltazar Ortiz Montejano

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button: Button = findViewById(R.id.calc_button)
        button.setOnClickListener {
            val billOb = findViewById<EditText>(R.id.billQuery)
            val tipOb = findViewById<EditText>(R.id.tipQuery)
            val peopleOb = findViewById<EditText>(R.id.people_amount)
            if(!fieldsFull(billOb, tipOb,peopleOb)){
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show()
            }
            else{
                if(!validValues(billOb,tipOb,peopleOb)){
                    Toast.makeText(this, "Please give numeric values.", Toast.LENGTH_LONG).show()
                }
                else{
                    val tipp = calcTipAmount(billOb,tipOb)
                    val allInAll = calcTotalAmount(tipp,billOb)
                    val perEach = calcPerAmount(allInAll,peopleOb)
                    val tipOut = findViewById<TextView>(R.id.tipOutput)
                    val totOut = findViewById<TextView>(R.id.totOutput)
                    val amountOut = findViewById<TextView>(R.id.amountOutput)
                    tipOut.text = tipp
                    totOut.text = allInAll
                    amountOut.text = perEach
                }
            }
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
        }
        findViewById<LinearLayout>(R.id.verts).setOnClickListener{
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
        }
    }

    fun fieldsFull(
        billObject: EditText,
        tipPercentObject: EditText,
        peopleObject: EditText
    ): Boolean {
        val bill: String = billObject.text.toString()
        val tipPerc: String = tipPercentObject.text.toString()
        val people: String = peopleObject.text.toString()
        if (bill.equals("") || tipPerc.equals("") || people.equals("")) {
            return false
        }
        return true
    }

    fun validValues(cost : EditText, tip : EditText, people : EditText):Boolean{
        //if one of these is not a double then return false else return true
        val string = cost.text.toString()
        val string2 = tip.text.toString()
        val string3 = people.text.toString()
        var numeric = true
        try {
            val num = parseDouble(string)
            val num2 = parseDouble(string2)
            val num3 = parseDouble(string3)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
    }

    fun calcTipAmount(cost : EditText, tip : EditText):String{
        val costOfMeal = cost.text.toString().toDouble()
        val tipPercent = tip.text.toString().toDouble()
        val tipAmount = tipPercent / 100 * costOfMeal
        return String.format("%.2f", tipAmount)
    }

    fun calcTotalAmount(extra : String, cost : EditText):String{
        val costOfMeal = cost.text.toString().toDouble()
        val tipAmount = extra.toDouble()
        val total = tipAmount + costOfMeal
        return String.format("%.2f", total)
    }

    fun calcPerAmount(overall : String, people : EditText):String{
        val total = overall.toDouble()
        val divsior = people.text.toString().toDouble()
        val perPerson = total / divsior //need to handle people of 3.2
        return String.format("%.2f", perPerson)
    }
}
