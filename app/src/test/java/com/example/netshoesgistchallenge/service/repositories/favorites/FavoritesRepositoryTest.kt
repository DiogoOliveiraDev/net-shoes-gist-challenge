package com.example.netshoesgistchallenge.service.repositories.favorites

import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.gistEntity
import com.example.netshoesgistchallenge.service.repositories.filecontent.FileContentRepository
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity
import com.example.netshoesgistchallenge.service.repositories.gists.map.mapper.GistsMapper
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FavoritesRepositoryTest {

    private val appDatabase: AppDatabase = mockk(relaxed = true)
    private val mapper: GistsMapper = mockk(relaxed = true)
    private val fileContentRepository: FileContentRepository = mockk(relaxed = true)
    private val gistId = "gistId"

    private val favoritesRepository = FavoritesRepository(
        appDatabase,
        fileContentRepository,
        mapper
    )

    @Test
    fun `get all favorites test`() {
        // When
        runBlocking {
            favoritesRepository.getAllFavorites()
        }

        // Then
        verify (exactly = 1) {appDatabase.favoritesDao().getAll()  }
    }

    @Test
    fun `add as favorite test`() {
        // Given
        val listFavorites = listOf(gistEntity.copy(isFavorite = true))
        val content = "content"
        val fileContent = FileContentEntity(
            gistId = gistId,
            content = content
        )
        coEvery { fileContentRepository.getContentOfGist(gistId) } returns content
        coEvery { appDatabase.gistContentDao().insert(fileContent) } just runs

        // When
        runBlocking {
            favoritesRepository.addFavorite(gistId)
        }

        // Then
        coVerify (exactly = 1) { fileContentRepository.getContentOfGist(gistId)  }
        coVerify (exactly = 1) { appDatabase.gistContentDao().insert(fileContent)  }
    }

    @Test
    fun `remove favorite test`() {
        // When
        runBlocking {
            favoritesRepository.removeFavorite(gistId)
        }

        // Then
        verify (exactly = 1) { appDatabase.gistContentDao().deleteById(gistId)  }
        verify (exactly = 1) { appDatabase.favoritesDao().remove(gistId)  }
    }
}
