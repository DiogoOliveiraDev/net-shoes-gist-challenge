package com.example.netshoesgistchallenge.di

import com.example.netshoesgistchallenge.service.repositories.favorites.FavoritesRepository
import com.example.netshoesgistchallenge.service.repositories.filecontent.FileContentRepository
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper
import com.example.netshoesgistchallenge.service.repositories.gists.GistsRepository
import org.koin.dsl.module

val gitsByUserModule = module{
    factory { GistsMapper() }
    factory { GistsRepository(get(), get(), get()) }
    factory { FavoritesRepository(get(), get(), get ()) }
    factory { FileContentRepository(get(), get()) }
}
