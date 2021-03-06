package com.example.netshoesgistchallenge.global

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class CoroutineScopeProvider: ViewModel() {
    abstract val dispatcher: CoroutineDispatcher
}
