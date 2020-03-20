package com.example.hw2

import android.widget.EditText
import java.lang.Double

class TipCalculator{
    fun fieldsFull(bills: kotlin.Double?, tipPercent: kotlin.Double?, peoples: Int?): Boolean {
        if (bills == null || tipPercent == null || peoples == null) {
            return false
        }
        return true
    }

    fun validValues(cost : String, tip : String, people : String):Boolean{
        var numeric = true
        try {
            val num = Double.parseDouble(cost)
            val num2 = Double.parseDouble(tip)
            val num3 = Double.parseDouble(people)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
    }

    fun calcTipAmount(cost : String, tip : String):String{
        val costOfMeal = cost.toDouble()
        val tipPercent = tip.toDouble()
        val tipAmount = tipPercent / 100 * costOfMeal
        return String.format("%.2f", tipAmount)
    }

    fun calcTotalAmount(extra : String, cost : String):String{
        val costOfMeal = cost.toDouble()
        val tipAmount = extra.toDouble()
        val total = tipAmount + costOfMeal
        return String.format("%.2f", total)
    }

    fun calcPerAmount(overall : String, people : String):String{
        val total = overall.toDouble()
        val divsior = people.toInt()
        val perPerson = total / divsior //need to handle people of 3.2
        return String.format("%.2f", perPerson)
    }

}