package com.example.netshoesgistchallenge.features.home.viewmodel

import com.example.netshoesgistchallenge.features.home.state.HomeStateCreator
import com.example.netshoesgistchallenge.gistMap
import com.example.netshoesgistchallenge.instantLiveDataAndCoroutineRules
import com.example.netshoesgistchallenge.service.repositories.favorites.FavoritesRepository
import com.example.netshoesgistchallenge.service.repositories.gists.GistsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class HomeViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule = instantLiveDataAndCoroutineRules

    private val gistsRepository: GistsRepository = mockk(relaxed = true)
    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)
    private val stateCreator: HomeStateCreator = mockk(relaxed = true)
    private val viewModel = HomeViewModel(
        gistsRepository,
        favoritesRepository,
        stateCreator,
        Dispatchers.Unconfined
    )
    private val user = "user"

    @ExperimentalCoroutinesApi
    @Test
    fun `should get new Gists`() =
        runBlockingTest {
            // Given
            coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf(gistMap)

            // When
            viewModel.getGists(GistSearchMode.NEW_GISTS)

            // Then
            coVerify (exactly = 1) { gistsRepository.getNewGists(any(), any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get favorites Gists`() =
        runBlockingTest {
            // When
            viewModel.getGists(GistSearchMode.FAVORITES, user)

            // Then
            coVerify (exactly = 1) { favoritesRepository.getAllFavorites() }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return error favorites Gists`() =
        runBlockingTest {
            // Given
            every { favoritesRepository.getAllFavorites() } throws Exception()

            // When
            viewModel.getGists(GistSearchMode.FAVORITES)

            // Then
            coVerify (exactly = 1) { favoritesRepository.getAllFavorites() }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get user Gists`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } returns listOf(gistMap)

        // When
        viewModel.getGists(GistSearchMode.BY_USER, user)

        // Then
        coVerify (exactly = 1) { gistsRepository.getAllDataByUser(user, any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should reset last search parameters and change search mode`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf(gistMap)
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } returns listOf(gistMap)

        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS, user)
        viewModel.getGists(GistSearchMode.BY_USER, user)

        // Then
        coVerify (exactly = 1) { gistsRepository.getAllDataByUser(user, any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get more results without know if there more results`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf(gistMap)

        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS, user)
        viewModel.getMoreGits()

        // Then
        coVerify (exactly = 2) { gistsRepository.getNewGists(any(), any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should stop to try get more results after error for new Gists`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf(gistMap)
        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS, user)
        coEvery { gistsRepository.getNewGists(any(), any()) } throws HttpException(Response.success(""))
        viewModel.getMoreGits()
        viewModel.getMoreGits()

        // Then
        coVerify (exactly = 2) { gistsRepository.getNewGists(any(), any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should stop to try get more results after error for user Gists`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } returns listOf(gistMap)
        // When
        viewModel.getGists(GistSearchMode.BY_USER, user)
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } throws HttpException(Response.success(""))
        viewModel.getMoreGits()
        viewModel.getMoreGits()

        // Then
        coVerify (exactly = 2) { gistsRepository.getAllDataByUser(user, any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should stop to try get more results after response 200 with empty list`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } returns listOf()
        // When
        viewModel.getGists(GistSearchMode.BY_USER, user)
        coEvery { gistsRepository.getAllDataByUser(any(), any()) } returns listOf()
        viewModel.getMoreGits()
        viewModel.getMoreGits()

        // Then
        coVerify (exactly = 3) { gistsRepository.getAllDataByUser(user, any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should add favorites and update current item in list with favorites`() = runBlockingTest {
        // Given
        val listMap = listOf(gistMap.copy(gistId = "nonFavoriteItem"), gistMap)
        val gistId = gistMap.gistId
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listMap

        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS)
        viewModel.onChangeFavoriteStatus(gistId, true)

        // Then
        coVerify (exactly = 1) { favoritesRepository.addFavorite(gistId) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should remove favorites and update current item in list with favorites`() = runBlockingTest {
        // Given
        val listMap = listOf(gistMap)
        val gistId = gistMap.gistId
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listMap

        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS)
        viewModel.onChangeFavoriteStatus(gistId, false)

        // Then
        coVerify (exactly = 1) { favoritesRepository.removeFavorite(gistId) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should reset list of new gists when use swipe refresh`() = runBlockingTest {
        // Given
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf()

        // When
        viewModel.onSwipeRefresh(true)

        // Then
        coVerify (exactly = 1) { gistsRepository.getNewGists(any(), any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should filter current list of Gists by user`() = runBlockingTest {
        // Given
        val userId = gistMap.owner.login
        coEvery { gistsRepository.getNewGists(any(), any()) } returns listOf(gistMap)

        // When
        viewModel.getGists(GistSearchMode.NEW_GISTS)
        viewModel.filterCurrentUserOnList(userId)

        // Then
        coVerify (exactly = 2) {  stateCreator.createGistItems(any()) }
    }
}