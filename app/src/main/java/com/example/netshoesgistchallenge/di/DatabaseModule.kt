package com.example.netshoesgistchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module{
    single { getAppDatabase(androidContext()) }
}

fun getAppDatabase(appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            appContext.resources.getString(R.string.app_database_name)
        ).build()
