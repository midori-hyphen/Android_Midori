package com.example.andoriod_midori

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleInstrumentedTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.andoriod_midori", appContext.packageName)
    }
    
    @Test
    fun mainActivityLaunches() {
        onView(withId(android.R.id.content)).check(matches(isDisplayed()))
    }
    
    @Test
    fun appHasCorrectTitle() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitle = appContext.getString(R.string.app_name)
        assertEquals("Midori", expectedTitle)
    }
}