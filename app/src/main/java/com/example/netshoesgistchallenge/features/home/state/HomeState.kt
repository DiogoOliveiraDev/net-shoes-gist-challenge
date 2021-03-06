package com.example.netshoesgistchallenge.features.home.state

data class HomeState(
    val shimmerVisibility: Boolean = false,
    val errorMessage: String = "",
    val errorLayoutVisible: Boolean,
    val isLoading: Boolean,
    val thereIsNotMoreResults: Boolean = false,
    val showDialogFilter: Boolean = false,
)
