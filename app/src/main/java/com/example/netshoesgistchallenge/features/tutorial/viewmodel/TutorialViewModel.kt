package com.example.netshoesgistchallenge.features.tutorial.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netshoesgistchallenge.features.tutorial.state.TutorialState
import com.example.netshoesgistchallenge.features.tutorial.state.TutorialStateCreator

class TutorialViewModel(
    private val state: TutorialStateCreator
) : ViewModel() {

    private var currentPage = 0
    set(value) {
        if (field == value) return
        createStateAndPost(value)
        field = value
    }

    companion object {
        const val STEP_1 = 0
        const val STEP_2 = 1
        const val STEP_3 = 2
        const val STEP_4 = 3
    }

    val stateStream: MutableLiveData<TutorialState> = MutableLiveData()

    fun onPageChange(currentPage: Int) {
        this.currentPage = currentPage
    }

    private fun createStateAndPost(currentPage: Int) {
        stateStream.postValue(
            when(currentPage) {
                STEP_1 ->  state.crateDefaultState()
                STEP_2 ->  state.crateStep2State(currentPage)
                STEP_3 ->  state.crateStep3State(currentPage)
                STEP_4 ->  state.crateStep4State(currentPage)
                else -> state.crateDefaultState()
            }
        )
    }

    fun createInitialState() {
        stateStream.postValue(
                state.crateDefaultState()
        )
    }

    fun onClickBack(currentItem: Int) {
        createStateAndPost(currentItem - 1)
    }

    fun onClickNext(currentItem: Int) {
        createStateAndPost(currentItem + 1)
    }
}
