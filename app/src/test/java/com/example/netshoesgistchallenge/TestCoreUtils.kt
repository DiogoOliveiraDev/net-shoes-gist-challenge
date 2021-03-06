package com.example.netshoesgistchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain

val instantLiveDataAndCoroutineRules: RuleChain
get() = RuleChain
    .outerRule(InstantCoroutineDispatcherRule())
    .around(InstantTaskExecutorRule())