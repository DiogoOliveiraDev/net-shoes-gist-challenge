package com.example.netshoesgistchallenge.service.deserialization

import com.example.netshoesgistchallenge.service.repositories.gists.entity.FilesEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GenericFileEmbedded
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

object DeserializationUtils {

    fun deserializeFile(jsonObject: JsonObject) =
        FilesEmbedded(
            fileEmbedded = Gson().fromJson(
                getJsonContentFromFilesObject(jsonObject.get("files")),
                GenericFileEmbedded::class.java
            )
        )

    private fun getJsonContentFromFilesObject(jsonElement: JsonElement) =
        (Gson().toJsonTree(jsonElement) as JsonObject)
            .entrySet()
            .stream()
            .findFirst()
            .get()
            .value
}
