package com.example.netshoesgistchallenge.di

import com.example.netshoesgistchallenge.service.apis.FileContentService
import com.example.netshoesgistchallenge.service.apis.GistsService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    factory { provideGistService(get()) }
    factory { provideFileContentervice(get()) }
}

fun provideGistService(retrofit: Retrofit): GistsService =
        retrofit.create(
                GistsService::class.java,
        )

fun provideFileContentervice(retrofit: Retrofit): FileContentService =
        retrofit.create(
                FileContentService::class.java,
        )
