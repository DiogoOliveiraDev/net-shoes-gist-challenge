package com.example.netshoesgistchallenge.features.gistdetails.viewmodel

import com.example.netshoesgistchallenge.features.gistdetails.state.GistDetailsStateCreator
import com.example.netshoesgistchallenge.gistMap
import com.example.netshoesgistchallenge.instantLiveDataAndCoroutineRules
import com.example.netshoesgistchallenge.service.repositories.filecontent.FileContentRepository
import com.example.netshoesgistchallenge.service.repositories.gists.GistsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class GistDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = instantLiveDataAndCoroutineRules

    private val fileContentRepository: FileContentRepository = mockk(relaxed = true)
    private val gistsRepository: GistsRepository = mockk(relaxed = true)
    private val gistDetailsStateCreator: GistDetailsStateCreator  = mockk(relaxed = true)
    private val viewModel = GistDetailsViewModel(
        fileContentRepository,
        gistsRepository,
        gistDetailsStateCreator
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `should get file content by id2`() =
        runBlockingTest {
            // Given
            val fileContent = "Hi I'am file content!"
            val gistId = "gistId"
            coEvery { gistsRepository.getGistById(gistId) } returns gistMap
            coEvery { fileContentRepository.getContentOfGist(gistId) } returns fileContent

            // When
            viewModel.getFileContentById(gistId)

            coVerify (atLeast = 1) { gistsRepository.getGistById(gistId) }
            verify (atLeast = 1) { gistDetailsStateCreator.create(gistMap, fileContent) }
            coVerify (atLeast = 1) { fileContentRepository.getContentOfGist(gistId) }
        }
}
