package com.example.netshoesgistchallenge.features.gistdetails.state

import com.example.netshoesgistchallenge.R

data class GistDetailsState(
    val user: String,
    val avatarUrl: String,
    val content: String,
    val language: String,
    val contentType: String,
    val fileName: String,
    val contentErrorVisibility: Boolean = false,
    val errorMessageResource: Int = R.string.failed_to_get_content
)
