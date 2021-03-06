package com.example.netshoesgistchallenge.features.gistdetails.state

import com.example.netshoesgistchallenge.common.Constants
import com.example.netshoesgistchallenge.gistMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.FilesMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GenericFileMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.OwnerMap
import org.junit.Assert.*
import org.junit.Test

class GistDetailsStateCreatorTest {

    private val gistDetailsStateCreator = GistDetailsStateCreator()

    @Test
    fun `should create an error state`() {
        // When
        val result = gistDetailsStateCreator.createError()

        // Then
        assertEquals(Constants.EMPTY, result.user)
        assertEquals(Constants.EMPTY, result.avatarUrl)
        assertEquals(Constants.EMPTY, result.content)
        assertEquals(Constants.EMPTY, result.language)
        assertEquals(Constants.EMPTY, result.contentType)
        assertEquals(Constants.EMPTY, result.fileName)
        assertTrue(result.contentErrorVisibility)
    }

    @Test
    fun `should create an sucess state`() {
        // When
        val fileContent = "Hi Iam a file content! =)"
        val result = gistDetailsStateCreator.create(gistMap, fileContent)

        // Then
        assertEquals(gistMap.owner.login, result.user)
        assertEquals(gistMap.owner.avatarUrl, result.avatarUrl)
        assertEquals(fileContent, result.content)
        assertEquals(gistMap.files.file.language, result.language)
        assertEquals(gistMap.files.file.type, result.contentType)
        assertEquals(gistMap.files.file.fileName, result.fileName)
        assertFalse(result.contentErrorVisibility)
    }
}
