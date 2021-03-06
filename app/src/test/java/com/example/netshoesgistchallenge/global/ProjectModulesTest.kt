package com.example.netshoesgistchallenge.global

import android.content.Context
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.di.allModules
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

class ProjectModulesTest : KoinTest {

    private val context: Context = mockk()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(context)
        modules(allModules)
    }

    @Before
    fun setUp() {
        every {
            context.resources.getString(R.string.app_database_name)
        } returns "random"
        every {
            context.resources.getString(R.string.app_base_host)
        } returns "randomHost"
    }

    @Test
    fun test() {

    }

    @After
    fun tearDown() {
        unloadKoinModules(allModules)
    }
}