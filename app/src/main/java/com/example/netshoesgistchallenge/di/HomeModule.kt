package com.example.netshoesgistchallenge.di

import com.example.netshoesgistchallenge.features.home.state.HomeStateCreator
import com.example.netshoesgistchallenge.features.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module{
    factory { HomeStateCreator() }
    viewModel { HomeViewModel(get(), get(), get(), Dispatchers.IO) }
}
