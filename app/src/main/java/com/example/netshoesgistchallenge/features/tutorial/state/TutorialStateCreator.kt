package com.example.netshoesgistchallenge.features.tutorial.state

import com.example.netshoesgistchallenge.R

class TutorialStateCreator {
    fun crateDefaultState () = TutorialState()

    fun crateStep2State(position: Int) =
            TutorialState(
                    pagerPosition = position,
                    nextButtonVisibility = true,
                    backButtonVisibility = true,
                    tvDescriptionResourceId = R.string.tutorial_step2_description
            )

    fun crateStep3State(position: Int) =
            TutorialState(
                    pagerPosition = position,
                    nextButtonVisibility = true,
                    backButtonVisibility = true,
                    tvDescriptionResourceId = R.string.tutorial_step3_description
            )

    fun crateStep4State(position: Int) =
            TutorialState(
                    pagerPosition = position,
                    nextButtonVisibility = false,
                    backButtonVisibility = true,
                    tvCloseVisibility = true,
                    tvDescriptionResourceId = R.string.tutorial_step4_description
            )
}
