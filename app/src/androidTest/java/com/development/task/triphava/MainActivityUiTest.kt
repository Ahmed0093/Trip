package com.development.task.triphava

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.development.task.triphava.repository.CANCELED_STATUS
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    @Rule
    @JvmField
    var activityActivityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {

        }


    @Test
    @Throws(Exception::class)
    fun StartTheListApp() {
        Thread.sleep(6000)
        onView(withId(R.id.search_btn)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
    }


    @Test
    @Throws(Exception::class)
    fun SatrtListAppWIthIncludeSwitchIsOn_Click_On_Canceled_Trip() {
        Thread.sleep(6000)
        onView(withId(R.id.switch1)).perform(click())
        onView(withId(R.id.search_btn)).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )
        Thread.sleep(3000)


    }


}