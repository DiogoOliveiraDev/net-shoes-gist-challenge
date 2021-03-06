package com.example.netshoesgistchallenge.global

import android.app.Application
import android.util.Log
import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.di.allModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.Exception

class App : Application() {

    private val appDatabase by inject<AppDatabase>()

    override fun onCreate() {
        super.onCreate()

        initializeKoinModules()

        runBlocking {
            clearDatabase()
        }
    }

     private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            appDatabase.gistsDao().clear()
        }
    }

    private fun initializeKoinModules() {
        startKoin{
            androidContext(this@App)
            modules(allModules)
        }
    }
}
