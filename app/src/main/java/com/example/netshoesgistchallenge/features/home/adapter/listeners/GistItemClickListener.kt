package com.example.netshoesgistchallenge.features.home.adapter.listeners

interface GistItemClickListener {
    fun onItemClick(
        gistId: String,
        avatarUrl: String,
        user: String,
        language: String
    )
}
