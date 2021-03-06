package com.example.netshoesgistchallenge.service.repositories.gists.map

data class GistMap(
        val url: String,
        val gistId: String,
        val files: FilesMap,
        val owner: OwnerMap,
        val isFavorite: Boolean
)
