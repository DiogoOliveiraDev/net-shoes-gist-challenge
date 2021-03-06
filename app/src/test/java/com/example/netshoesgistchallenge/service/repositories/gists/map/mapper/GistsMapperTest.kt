package com.example.netshoesgistchallenge.service.repositories.gists.map.mapper

import com.example.netshoesgistchallenge.gistEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test


internal class GistsMapperTest {
    private val gistsMapper = GistsMapper()

    @Test
    fun `create Map From Entity`() {
        // When
        val result = gistsMapper.toMap(listOf(gistEntity))
        val first = result[0]

        // Then
        assertTrue(result.isNotEmpty())
        assertEquals(gistEntity.url, first.url)
        assertEquals(gistEntity.gistId, first.gistId)
        assertEquals(gistEntity.files.fileEmbedded.fileName, first.files.file.fileName)
        assertEquals(gistEntity.files.fileEmbedded.type, first.files.file.type)
        assertEquals(gistEntity.files.fileEmbedded.language, first.files.file.language)
        assertEquals(gistEntity.files.fileEmbedded.contentUrl, first.files.file.contentUrl)
        assertEquals(gistEntity.owner.login, first.owner.login)
        assertEquals(gistEntity.owner.userId, first.owner.id)
        assertEquals(gistEntity.owner.avatarUrl, first.owner.avatarUrl)
        assertEquals(gistEntity.owner.gitHubHomePageUrl, first.owner.gitHubHomePage)
    }
}
