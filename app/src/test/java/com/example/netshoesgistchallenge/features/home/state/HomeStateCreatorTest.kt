package com.example.netshoesgistchallenge.features.home.state

import com.example.netshoesgistchallenge.gistEntity
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class HomeStateCreatorTest {

    private val stateCreator = HomeStateCreator()
    private val gistMapper = GistsMapper()

    @Test
    fun `should create gist items from gist map`() {
        // Given
        val list = listOf(gistEntity)

        // When
        val result = stateCreator.createGistItems(
            gistMapper.toMap(
                list
            )
        )

        // Then
        assertTrue(result.isNotEmpty())
        assertEquals(list.size, result.size)
        assertEquals(gistEntity.gistId, result[0].gistId)
        assertEquals(gistEntity.owner.login, result[0].user)
        assertEquals(gistEntity.owner.avatarUrl, result[0].avatarUrl)
        assertEquals(gistEntity.files.fileEmbedded.type, result[0].contentType)
        assertEquals(gistEntity.files.fileEmbedded.fileName, result[0].fileTitle)
        assertEquals(gistEntity.files.fileEmbedded.language, result[0].language)
        assertEquals(gistEntity.isFavorite, result[0].isFavorite)
    }

    @Test
    fun `should stop fetch items state in recyclerView`() {
        // Given
        val errorLayoutVisible = false
        val isLoading = false
        val thereIsNotMoreResults = true

        // When
        val result = stateCreator.showError(true)

        // Then
        assertEquals(errorLayoutVisible, result.errorLayoutVisible)
        assertEquals(isLoading, result.isLoading)
        assertEquals(thereIsNotMoreResults, result.thereIsNotMoreResults)
    }

    @Test
    fun `should manage loading status and shimmer as well`() {
        // Given
        val showShimmer = true
        val isLoading = false

        // When
        val result = stateCreator.manageLoading(isLoading, showShimmer)

        // Then
        assertEquals(isLoading, result.isLoading)
        assertEquals(showShimmer, result.shimmerVisibility)
    }
}