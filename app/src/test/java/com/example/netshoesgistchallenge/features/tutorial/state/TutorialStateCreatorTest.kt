package com.example.netshoesgistchallenge.features.tutorial.state

import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.features.tutorial.viewmodel.TutorialViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class TutorialStateCreatorTest {

    private val tutorialStateCreator = TutorialStateCreator()

    @Test
    fun `should create default state` () {
        // When
        val result = tutorialStateCreator.crateDefaultState()

        // Then
        assertEquals(true, result.nextButtonVisibility)
        assertEquals(false, result.backButtonVisibility)
        assertEquals(false, result.tvCloseVisibility)
        assertEquals(R.string.tutorial_step1_description, result.tvDescriptionResourceId)
    }

    @Test
    fun `should create step2 state` () {
        // When
        val result = tutorialStateCreator.crateStep2State(TutorialViewModel.STEP_2)

        // Then
        assertEquals(true, result.nextButtonVisibility)
        assertEquals(true, result.backButtonVisibility)
        assertEquals(false, result.tvCloseVisibility)
        assertEquals(R.string.tutorial_step2_description, result.tvDescriptionResourceId)
    }

    @Test
    fun `should create step3 state` () {
        // When
        val result = tutorialStateCreator.crateStep3State(TutorialViewModel.STEP_3)

        // Then
        assertEquals(true, result.nextButtonVisibility)
        assertEquals(true, result.backButtonVisibility)
        assertEquals(false, result.tvCloseVisibility)
        assertEquals(R.string.tutorial_step3_description, result.tvDescriptionResourceId)
    }

    @Test
    fun `should create step4 state` () {
        // When
        val result = tutorialStateCreator.crateStep4State(TutorialViewModel.STEP_4)

        // Then
        assertEquals(false, result.nextButtonVisibility)
        assertEquals(true, result.backButtonVisibility)
        assertEquals(true, result.tvCloseVisibility)
        assertEquals(R.string.tutorial_step4_description, result.tvDescriptionResourceId)
    }
}
