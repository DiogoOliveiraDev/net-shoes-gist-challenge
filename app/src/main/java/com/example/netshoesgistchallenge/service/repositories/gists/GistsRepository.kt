package com.example.netshoesgistchallenge.service.repositories.gists

import com.example.netshoesgistchallenge.common.Constants.ITEMS_PER_PAGE
import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.service.apis.GistsService
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper

class GistsRepository(
        private val database: AppDatabase,
        private val api: GistsService,
        private val mapper: GistsMapper
) {

    suspend fun getNewGists(page: Int, since: String): List<GistMap> {
        val listFromService = api.getNewGists(page, ITEMS_PER_PAGE, since)
        val finalList = updateGistListWithFavorites(
                listFromService
        )
        saveGistOnDatabase(finalList)

        return mapper.toMap(finalList)
    }

    private fun updateGistListWithFavorites(listFromService: List<GistEntity>) : List<GistEntity> {
        val finalList = listFromService.toMutableList()

        listFromService.mapIndexed { index, element ->
          var entity = element.copy()

            database.gistsDao().getAll().forEach {
                if (element.gistId == it.gistId) {
                    entity = it.copy()
                }
            }

            finalList.set(index, entity)
        }

        return finalList
    }

    suspend fun getGistById(gistId: String) =
        mapper.convertToMap(
            database.gistsDao().getById(gistId) ?:
            api.getGistById(gistId).let {
                database.gistsDao().insert(it)
                it
        })

    suspend fun getAllDataByUser(user: String, page: Int) =
        mapper.toMap(
            updateGistListWithFavorites(
                getFromRemoteByUser(
                    user,
                    page
                )
            )
        )

    private suspend fun getFromRemoteByUser(user: String, page: Int) =
        api.getGistsFromUser(
            user,
            ITEMS_PER_PAGE,
            page
        ).let {
            saveGistOnDatabase(it)
            it
        }

    private fun saveGistOnDatabase(input: List<GistEntity>) =
            database.gistsDao().insertAll(input)
}
