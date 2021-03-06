package com.example.netshoesgistchallenge.service.repositories.gists.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class GenericFileEmbedded(
    @SerializedName("filename")
    @ColumnInfo(name = "filename")
    val fileName: String,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,

    @SerializedName("language")
    @ColumnInfo(name = "language")
    val language: String?,

    @SerializedName("raw_url")
    @ColumnInfo(name = "raw_url")
    val contentUrl: String
)
