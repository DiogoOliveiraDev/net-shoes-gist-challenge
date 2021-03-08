package com.example.netshoesgistchallenge.di

import com.example.netshoesgistchallenge.features.tutorial.state.TutorialStateCreator
import com.example.netshoesgistchallenge.features.tutorial.viewmodel.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tutorialModule = module{
    factory { TutorialStateCreator() }
    viewModel { TutorialViewModel(get()) }
}
