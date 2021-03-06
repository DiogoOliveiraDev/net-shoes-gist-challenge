package com.example.netshoesgistchallenge.service.repositories.gists

import com.example.netshoesgistchallenge.common.Constants
import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.database.daos.GistsDao
import com.example.netshoesgistchallenge.gistEntity
import com.example.netshoesgistchallenge.gistMap
import com.example.netshoesgistchallenge.service.apis.GistsService
import com.example.netshoesgistchallenge.service.repositories.RepositoryUtils.nowISOAsString
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class GistsRepositoryTest {

    private val gistsDao: GistsDao = mockk(relaxed = true)
    private val database: AppDatabase = mockk(relaxed = true)
    private val api: GistsService = mockk(relaxed = true)
    private val mapper: GistsMapper = mockk(relaxed = true)
    private val gistsRepository: GistsRepository = GistsRepository(database, api, mapper)
    private val user = "I am user"
    private val list = listOf(gistEntity)

    @Test
    fun `data come from service and persist Test`() {
        // Given
        val page = 0
        every { mapper.convertFileContentEntity(any()) } returns mockk()
        coEvery { api.getGistsFromUser(any(), any(), any()) } returns list
        every { database.gistsDao() } returns gistsDao
        every { database.gistsDao().getAllByUser(any()) } returns listOf()

        // When
        runBlocking {
            gistsRepository.getAllDataByUser(user, page)
        }

        // Then
        coVerifyOrder {
            api.getGistsFromUser(user, Constants.ITEMS_PER_PAGE, page)
            database.gistsDao().insertAll(list)
            database.gistsDao().getAll()
        }

        confirmVerified(api)
        confirmVerified(database)
    }

    @Test
    fun `data come from service and list is empty`() {
        // Given
        coEvery { api.getGistsFromUser(any(), any(), any()) } returns listOf()

        // When
        runBlocking {
            gistsRepository.getAllDataByUser(user, 0)
        }

        // Then
        verify  (exactly = 1) { database.gistsDao() }
        coVerify (exactly = 1) { api.getGistsFromUser(user, Constants.ITEMS_PER_PAGE, 0) }
    }

    @Test
    fun `should return Gist by id from local`() {
        // Given
        val gistId = "gistId"
        coEvery { database.gistsDao().getById(any()) } returns gistEntity

        // When
        runBlocking {
            gistsRepository.getGistById(gistId)
        }

        // Then
        verify  (exactly = 1) { database.gistsDao().getById(gistId) }
    }

    @Test
    fun `should return Gist by id from remote`() {
        // Given
        val gistId = "gistId"
        coEvery { database.gistsDao().getById(any()) } returns null
        coEvery { api.getGistById(any()) } returns gistEntity

        // When
        runBlocking {
            gistsRepository.getGistById(gistId)
        }

        // Then
        coVerifyOrder {
            database.gistsDao().getById(gistId)
            api.getGistById(gistId)
            database.gistsDao().insert(gistEntity)
        }

        confirmVerified(database)
        confirmVerified(api)

        verify  (atLeast = 1) { database.gistsDao().getById(gistId) }
        coVerify (atLeast = 1) { api.getGistById(gistId) }
        verify  (atLeast = 1) { database.gistsDao().insert(gistEntity) }
    }

    @Test
    fun `should get new Gists from service`() {
        // Given
        val page = 1
        val since = nowISOAsString()
        coEvery { api.getNewGists(page, Constants.ITEMS_PER_PAGE, since) } returns list

        // When
        runBlocking {
            gistsRepository.getNewGists(page,  nowISOAsString())
        }

        // Then
        verify  (atLeast = 1) { database.gistsDao().insertAll(list) }
    }

    @Test
    fun `should update Gist list with favorites in database`() {
        // Given
        val page = 1
        val since = nowISOAsString()
        val listFromDatabaseFavorites = listOf(gistEntity.copy(isFavorite = true))
        coEvery { api.getNewGists(page, Constants.ITEMS_PER_PAGE, since) } returns list
        coEvery { database.gistsDao().getAll() } returns listFromDatabaseFavorites
        every { mapper.toMap(listFromDatabaseFavorites) } returns listOf(gistMap.copy(isFavorite = true))

        // When
        runBlocking {
            // Then
            gistsRepository.getNewGists(page,  nowISOAsString()).apply {
                assertTrue(this.isNotEmpty())
                assertTrue(this[0].isFavorite)
            }
        }
    }
}
