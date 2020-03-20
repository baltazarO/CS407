package com.example.hw2

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule


    @RunWith(AndroidJUnit4::class)
    @LargeTest
    class TipCalculatorInstrumentedTest {

        @get:Rule
        var activityRule: ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java)

        @Test
        fun test_calculate_tip() {

            Espresso.onView(ViewMatchers.withId(R.id.billQuery))
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("100.00"))

            Espresso.onView(ViewMatchers.withId(R.id.tipQuery))
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("20"))

            Espresso.onView(ViewMatchers.withId(R.id.people_amount))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("2"))

            Espresso.onView(ViewMatchers.withId(R.id.calc_button))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

            Espresso.onView(ViewMatchers.withId(R.id.tipOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("20.00")))

            Espresso.onView(ViewMatchers.withId(R.id.totOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("120.00")))

            Espresso.onView(ViewMatchers.withId(R.id.amountOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("60.00")))

        }

        @Test
        fun test_empty_fields() {

            Espresso.onView(ViewMatchers.withId(R.id.calc_button))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

            Espresso.onView(ViewMatchers.withId(R.id.tipOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("")))

            Espresso.onView(ViewMatchers.withId(R.id.totOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("")))

            Espresso.onView(ViewMatchers.withId(R.id.amountOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("")))

        }

        @Test
        fun test_bad_values() {

            Espresso.onView(ViewMatchers.withId(R.id.billQuery))
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("1cool00.00"))

            Espresso.onView(ViewMatchers.withId(R.id.tipQuery))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("2james_harden0"))

            Espresso.onView(ViewMatchers.withId(R.id.people_amount))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("2mikey"))


            Espresso.onView(ViewMatchers.withId(R.id.calc_button))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

            Espresso.onView(ViewMatchers.withId(R.id.tipOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("20.00")))

            Espresso.onView(ViewMatchers.withId(R.id.totOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("120.00")))

            Espresso.onView(ViewMatchers.withId(R.id.amountOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("60.00")))
        }

        @Test
        fun test_rounding() {

            Espresso.onView(ViewMatchers.withId(R.id.billQuery))
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("15.99"))

            Espresso.onView(ViewMatchers.withId(R.id.tipQuery))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("20"))

            Espresso.onView(ViewMatchers.withId(R.id.people_amount))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())
                .perform(ViewActions.typeText("2"))


            Espresso.onView(ViewMatchers.withId(R.id.calc_button))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

            Espresso.onView(ViewMatchers.withId(R.id.tipOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("3.20")))

            Espresso.onView(ViewMatchers.withId(R.id.totOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("19.19")))

            Espresso.onView(ViewMatchers.withId(R.id.amountOutput))
                .perform(ViewActions.scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.withText("9.60")))
        }
    }