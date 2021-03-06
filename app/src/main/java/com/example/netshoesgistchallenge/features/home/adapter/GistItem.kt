package com.example.netshoesgistchallenge.features.home.adapter

data class GistItem(
        val gistId: String,
        val user: String,
        val avatarUrl: String,
        val contentType: String,
        val fileTitle: String,
        val language: String,
        val isFavorite: Boolean
)
