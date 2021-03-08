package com.example.netshoesgistchallenge.di

import com.example.netshoesgistchallenge.features.gistdetails.state.GistDetailsStateCreator
import com.example.netshoesgistchallenge.features.gistdetails.viewmodel.GistDetailsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module{
    factory { GistDetailsStateCreator() }
    viewModel { GistDetailsViewModel(get(), get(), get(), Dispatchers.IO) }
}
