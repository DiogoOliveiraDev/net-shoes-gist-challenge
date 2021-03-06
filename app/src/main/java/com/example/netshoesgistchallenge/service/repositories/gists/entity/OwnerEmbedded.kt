package com.example.netshoesgistchallenge.service.repositories.gists.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class OwnerEmbedded(
    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @SerializedName("id")
    @ColumnInfo(name = "user_id")
    val userId: Long,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @SerializedName("url")
    @ColumnInfo(name = "git_hub_home_page_url")
    val gitHubHomePageUrl: String
)
