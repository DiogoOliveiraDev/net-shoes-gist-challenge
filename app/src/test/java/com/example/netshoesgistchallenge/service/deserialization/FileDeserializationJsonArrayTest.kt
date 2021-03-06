package com.example.netshoesgistchallenge.service.deserialization

import com.example.netshoesgistchallenge.gistEntity
import com.example.netshoesgistchallenge.service.deserialization.FileDeserializationJsonArray
import com.example.netshoesgistchallenge.service.repositories.gists.entity.FilesEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GenericFileEmbedded
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonParser
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.lang.reflect.Type


class FileDeserializationJsonArrayTest {

    private val typeOfT: Type = mockk(relaxed = true)
    private val context: JsonDeserializationContext = mockk(relaxed = true)

    private val customDeserialization = FileDeserializationJsonArray()

    @Test
    fun `should deserialize Json array With files fields generic Test`() {
        // Given
        val aleatoryFile = "random.txt"
        val fieldToReplaceSimulateApiJson = FilesEmbedded::class.java.declaredFields.first().name
        val json = Gson().toJson(
            listOf(gistEntity)
        ).replace(fieldToReplaceSimulateApiJson, aleatoryFile)

        // When
        val result = customDeserialization.deserialize(
            JsonParser.parseString(json),
            typeOfT,
            context
        )

        // Then
        assertTrue(json.contains(aleatoryFile))
        assertEquals(GenericFileEmbedded::class.java, result[0].files.fileEmbedded::class.java)
    }
}
