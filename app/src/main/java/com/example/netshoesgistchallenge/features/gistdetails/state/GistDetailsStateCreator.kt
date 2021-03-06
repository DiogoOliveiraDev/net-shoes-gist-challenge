package com.example.netshoesgistchallenge.features.gistdetails.state

import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap

class GistDetailsStateCreator {

    fun createError() = GistDetailsState(
        "",
        "",
        "",
        "",
        "",
        "",
    true
    )

    fun create(gistMap: GistMap, fileContent: String) =
        GistDetailsState(
            user = gistMap.owner.login,
            avatarUrl = gistMap.owner.avatarUrl,
            content = fileContent,
            language = gistMap.files.file.language,
            contentType = gistMap.files.file.type,
            fileName = gistMap.files.file.fileName
        )
}
