package com.example.hw2
import org.junit.Test

import org.junit.Assert.*

class TestTipCalculator {
    @Test
    fun test_fields_full() {
        val tip = TipCalculator()
        assertFalse(tip.fieldsFull(null,2.0,null))

        assertFalse(tip.fieldsFull(12.0,2.0,null))

        assertTrue(tip.fieldsFull(12.0,2.0,4))
    }

    @Test
    fun test_valid_values() {
        val tip = TipCalculator()
        assertTrue(tip.validValues("15.00","20","3"))

        assertFalse(tip.validValues("9h8h8","2e23e?","3"))

    }

    @Test
    fun test_calc_tip_amount() {
        val tip = TipCalculator()
        assertEquals("0.20",tip.calcTipAmount("20","1"))
        assertEquals("3.20",tip.calcTipAmount("15.99","20"))
    }

    @Test
    fun test_calc_total_amount() {
        val tip = TipCalculator()
        assertEquals("20.20",tip.calcTotalAmount("0.20","20"))
        assertEquals("19.19",tip.calcTotalAmount("3.20","15.99"))
    }

    @Test
    fun test_calc_per_amount() {
        val tip = TipCalculator()
        assertEquals("5.05",tip.calcPerAmount("20.20","4"))
        assertEquals("9.60",tip.calcPerAmount("19.19","2"))
    }
}