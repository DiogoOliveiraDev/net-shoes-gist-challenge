package com.example.netshoesgistchallenge.service.repositories.gists.entity

import androidx.room.Embedded

data class FilesEmbedded(
    @Embedded
    val fileEmbedded: GenericFileEmbedded
)
