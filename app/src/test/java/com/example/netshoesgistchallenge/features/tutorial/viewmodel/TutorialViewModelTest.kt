package com.example.netshoesgistchallenge.features.tutorial.viewmodel

import com.example.netshoesgistchallenge.features.tutorial.state.TutorialStateCreator
import com.example.netshoesgistchallenge.instantLiveDataAndCoroutineRules
import com.example.netshoesgistchallenge.tutorialState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class TutorialViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = instantLiveDataAndCoroutineRules

    private val tutorialStateCreator: TutorialStateCreator = mockk()
    private val viewModel = TutorialViewModel(tutorialStateCreator)

    @Test
    fun `should create initial state`() {
        // Given
        every { tutorialStateCreator.crateDefaultState() } returns tutorialState

        // When
        viewModel.createInitialState()

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateDefaultState() }
    }

    @Test
    fun `should create state for step2`() {
        // Given
        val position = TutorialViewModel.STEP_2

        every { tutorialStateCreator.crateStep2State(position) } returns tutorialState

        // When
        viewModel.onPageChange(position)

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateStep2State(position) }
    }

    @Test
    fun `should create state for step3`() {
        // Given
        val position = TutorialViewModel.STEP_3

        every { tutorialStateCreator.crateStep3State(position) } returns tutorialState

        // When
        viewModel.onPageChange(position)

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateStep3State(position) }
    }

    @Test
    fun `should create state for invalid step`() {
        // Given
        val position = 50
        every { tutorialStateCreator.crateDefaultState() } returns tutorialState

        // When
        viewModel.onPageChange(position)

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateDefaultState() }
    }

    @Test
    fun `should go to next step when click on next button`() {
        // Given
        val currentPosition = TutorialViewModel.STEP_1
        every { tutorialStateCreator.crateStep2State(any()) } returns tutorialState

        // When
        viewModel.onClickNext(currentPosition)

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateStep2State(currentPosition + 1) }
    }

    @Test
    fun `should go to back step when click on back button`() {
        // Given
        val currentPosition = TutorialViewModel.STEP_2
        every { tutorialStateCreator.crateDefaultState() } returns tutorialState

        // When
        viewModel.onClickBack(currentPosition)

        // Then
        verify (exactly = 1) { tutorialStateCreator.crateDefaultState() }
    }
}
