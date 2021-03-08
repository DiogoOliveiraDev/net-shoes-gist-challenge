package com.example.netshoesgistchallenge

import com.example.netshoesgistchallenge.features.tutorial.state.TutorialState
import com.example.netshoesgistchallenge.service.repositories.gists.entity.FilesEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GenericFileEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.service.repositories.gists.entity.OwnerEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.map.FilesMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GenericFileMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.OwnerMap

val gistEntity = GistEntity(
        url = "url",
        gistId = "gistId",
        files = FilesEmbedded(
                fileEmbedded = GenericFileEmbedded(
                        fileName = "fileName",
                        type = "type",
                        language = "language",
                        contentUrl = "contentUrl"
                )
        ),
        owner = OwnerEmbedded(
                login = "login",
                userId = "0000".toLong(),
                avatarUrl = "avatarUrl",
                gitHubHomePageUrl = "gitHubHomePageUrl"
        ),
        isFavorite = false
)

val gistMap = GistMap(
        url = gistEntity.url,
        gistId = gistEntity.gistId,
        files = FilesMap(
                file = GenericFileMap(
                        fileName = gistEntity.files.fileEmbedded.fileName,
                        type = gistEntity.files.fileEmbedded.type,
                        language = gistEntity.files.fileEmbedded.language!!,
                        contentUrl = gistEntity.files.fileEmbedded.contentUrl
                )
        ),
        owner = OwnerMap(
                login = gistEntity.owner.login,
                id = gistEntity.owner.userId,
                avatarUrl = gistEntity.owner.avatarUrl,
                gitHubHomePage = gistEntity.owner.gitHubHomePageUrl
        ),
        isFavorite = false
)

val tutorialState = TutorialState()
