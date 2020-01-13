package com.zengo.checkout

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zengo.checkout.views.fragments.InputFragment
import com.zengo.checkout.views.fragments.InputFragmentDirections
import org.hamcrest.CoreMatchers.not

import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class InputScreenInstrumentedTest {

    @Test
    fun textViewContentsTest()
    {
        val resultFragScenario = FragmentScenario.launchInContainer<InputFragment>(InputFragment::class.java)
        resultFragScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.cardNumberTextView)).check(matches(withText("Card number")))
        onView(withId(R.id.expiryDateTextView)).check(matches(withText("Expiry")))
    }

    @Test
    fun inputToCallPaymentBlockedTest()
    {
        // Create a mock NavController
        launchAndSetNavController()

        // Check the Pay button is NOT enabled
        onView(withId(R.id.button_pay)).check(matches(not(isEnabled())))
    }

    @Test
    fun inputToCallPaymentValidTest()
    {
        // Create a mock NavController
        val mockNavController = launchAndSetNavController()

        // fill in valid edit text values
        onView(withId(R.id.cardNumberEditText)).perform(typeText("1111-1111-1111-1111"))
        onView(withId(R.id.expiryDateEditText)).perform(typeText("06/2030"))
        onView(withId(R.id.cvvEditText)).perform(typeText("100"))

        // Check the Pay button IS enabled
        onView(withId(R.id.button_pay)).check(matches(isEnabled()))

        // Performing a click on Pay button
        onView(ViewMatchers.withId(R.id.button_pay)).perform(ViewActions.click())

        // Verify that performing above click performs the correct Navigation action
        val action = InputFragmentDirections.actionInputFragmentToCallCardPaymentFragment()
        verify(mockNavController).navigate(action)
    }

    // etc etc etc etc....



    private fun launchAndSetNavController(): NavController
    {
        // Create a mock NavController
        val mockNavController: NavController = mock(NavController::class.java)
        mockNavController.setGraph(R.navigation.navigation_checkout)

        // Create a graphical FragmentScenario
        val inputFragScenario = launchFragmentInContainer<InputFragment>()

        // Set the NavController property on the fragment
        inputFragScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        return mockNavController
    }
}
