package com.example.netshoesgistchallenge.di

import android.content.Context
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.service.deserialization.FileDeserializationJsonArray
import com.example.netshoesgistchallenge.service.deserialization.FileDeserializationJsonObject
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

var networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofit(get(), androidContext()) }
}

private fun provideOkHttpClient() = OkHttpClient.Builder().apply {
    this.addInterceptor(
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    )
}.build()

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    appContext: Context,
): Retrofit = Retrofit.Builder()
    .baseUrl(appContext.getString(R.string.app_base_host))
    .addConverterFactory(
            createGsonConverter(
                    GistEntity::class.java,
                    FileDeserializationJsonObject()
            )
    )
    .addConverterFactory(
            createGsonConverter(
                    object : TypeToken<MutableList<GistEntity>>() {}.type,
                    FileDeserializationJsonArray()
            )
    )
    .client(okHttpClient)
    .build()

private fun createGsonConverter(type: Type?, typeAdapter: Any?) : GsonConverterFactory {
    return if (type != null && typeAdapter != null) {
        GsonConverterFactory.create(
            GsonBuilder().registerTypeAdapter(
                type,
                typeAdapter
            ).create()
        )
    } else {
        GsonConverterFactory.create()
    }
}
