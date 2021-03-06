package com.example.netshoesgistchallenge.service.repositories.favorites

import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.service.repositories.filecontent.FileContentRepository
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper

class FavoritesRepository(
    private val appDatabase: AppDatabase,
    private val fileContentRepository: FileContentRepository,
    private val mapper: GistsMapper
) {

    fun getAllFavorites() =
            mapper.toMap(
                    appDatabase.favoritesDao().getAll()
            )

    suspend fun addFavorite(gistId: String) {
        fileContentRepository.getContentOfGist(gistId).apply {
            appDatabase.favoritesDao().add(gistId)
            appDatabase.gistContentDao().insert(
                    FileContentEntity(
                            gistId = gistId,
                            content = this
                    )
            )
        }
    }

    fun removeFavorite(gistId: String) {
        appDatabase.gistContentDao().deleteById(gistId)
        appDatabase.favoritesDao().remove(gistId)
    }
}
