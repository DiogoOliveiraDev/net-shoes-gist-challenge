package com.example.netshoesgistchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.netshoesgistchallenge.BuildConfig
import com.example.netshoesgistchallenge.database.daos.FavoritesDao
import com.example.netshoesgistchallenge.database.daos.GistContentDao
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.database.daos.GistsDao
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity

@Database(entities = [
    GistEntity::class,
    FileContentEntity::class
], version = BuildConfig.VERSION_CODE)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gistsDao(): GistsDao
    abstract fun gistContentDao() : GistContentDao
    abstract fun favoritesDao(): FavoritesDao
}
