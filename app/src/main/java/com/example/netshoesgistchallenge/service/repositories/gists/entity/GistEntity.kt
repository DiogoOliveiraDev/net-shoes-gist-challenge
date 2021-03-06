package com.example.netshoesgistchallenge.service.repositories.gists.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gists_by_user_entity", indices = [Index(value = ["gist_id"], unique = true)])
data class GistEntity (
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "gist_id")
    val gistId: String,

    @SerializedName("files")
    @Embedded
    val files: FilesEmbedded,

    @SerializedName("owner")
    @Embedded
    val owner: OwnerEmbedded,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
