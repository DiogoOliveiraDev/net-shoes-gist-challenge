package com.example.netshoesgistchallenge.service.deserialization

import com.example.netshoesgistchallenge.service.deserialization.DeserializationUtils.deserializeFile
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FileDeserializationJsonObject : JsonDeserializer<GistEntity> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ) = Gson().fromJson(
        json,
        GistEntity::class.java
    ).copy(files = deserializeFile(json.asJsonObject))
}
