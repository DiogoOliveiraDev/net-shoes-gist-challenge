package com.example.netshoesgistchallenge.service.repositories

import org.junit.Assert.assertTrue
import org.junit.Test

class RepositoryUtilsTest {

    @Test
    fun`should return a date format to request UTC gist`() {
        // When
        val result = RepositoryUtils.nowISOAsString()

        // Then
        assertTrue(result.contains("T"))
        assertTrue(result.contains("Z"))
    }
}