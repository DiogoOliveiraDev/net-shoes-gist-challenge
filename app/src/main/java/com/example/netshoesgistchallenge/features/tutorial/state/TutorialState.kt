package com.example.netshoesgistchallenge.features.tutorial.state

import com.example.netshoesgistchallenge.R

data class TutorialState(
    val pagerPosition: Int = 0,
    val nextButtonVisibility: Boolean = true,
    val backButtonVisibility: Boolean = false,
    val tvCloseVisibility: Boolean = false,
    val tvDescriptionResourceId: Int = R.string.tutorial_step1_description
)
