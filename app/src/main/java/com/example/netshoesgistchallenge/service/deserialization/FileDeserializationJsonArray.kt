package com.example.netshoesgistchallenge.service.deserialization

import com.example.netshoesgistchallenge.service.deserialization.DeserializationUtils.deserializeFile
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class FileDeserializationJsonArray : JsonDeserializer<List<GistEntity>> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ) = Gson().fromJson<List<GistEntity>>(
        json,
        object : TypeToken<List<GistEntity>>() {}.type
    ).mapIndexed { index, element ->
        element.copy(
            files = deserializeFile(
                    (json as JsonArray)[index].asJsonObject
            )
        )
    }
}
