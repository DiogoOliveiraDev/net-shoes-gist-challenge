package com.example.netshoesgistchallenge.service.repositories.gists.map.mapper

import com.example.netshoesgistchallenge.common.Constants.EMPTY
import com.example.netshoesgistchallenge.common.IToEntity
import com.example.netshoesgistchallenge.common.IToMap
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity
import com.example.netshoesgistchallenge.service.repositories.gists.entity.FilesEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GenericFileEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import com.example.netshoesgistchallenge.service.repositories.gists.entity.OwnerEmbedded
import com.example.netshoesgistchallenge.service.repositories.gists.map.FilesMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GenericFileMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap
import com.example.netshoesgistchallenge.service.repositories.gists.map.OwnerMap

class GistsMapper : IToMap<List<GistEntity>, List<GistMap>>, IToEntity<GistMap, GistEntity> {

    companion object {
        const val START_INDEX = 0
        const val MAX_CHAR = 25
    }

    override fun toMap(entity: List<GistEntity>) =
        entity.map {
            convertToMap(it)
        }.toList()

    fun convertToMap(entity: GistEntity) =
        GistMap(
                url = entity.url,
                gistId = entity.gistId,
                files = FilesMap(
                    file = GenericFileMap(
                        fileName = entity.files.fileEmbedded.fileName.formatFileName(),
                        type = entity.files.fileEmbedded.type,
                        language = entity.files.fileEmbedded.language ?: EMPTY,
                        contentUrl = entity.files.fileEmbedded.contentUrl
                    )
                ),
                owner = OwnerMap(
                    login = entity.owner.login,
                    id = entity.owner.userId,
                    avatarUrl = entity.owner.avatarUrl,
                    gitHubHomePage = entity.owner.gitHubHomePageUrl
                ),

                isFavorite = entity.isFavorite
        )

    fun convertFileContentEntity(fileContentEntity: FileContentEntity) =
            FileContentEntity(
                    gistId = fileContentEntity.gistId,
                    content = fileContentEntity.content
            )

    override fun toEntity(map: GistMap) = GistEntity(
            url = map.url,
            gistId = map.gistId,
            files = FilesEmbedded(
                    fileEmbedded = GenericFileEmbedded(
                            fileName = map.files.file.fileName,
                            type = map.files.file.type,
                            language = map.files.file.language,
                            contentUrl = map.files.file.contentUrl
                    )
            ),
            owner = OwnerEmbedded(
                    login = map.owner.login,
                    userId = map.owner.id,
                    avatarUrl = map.owner.avatarUrl,
                    gitHubHomePageUrl = map.owner.gitHubHomePage
            ),
            isFavorite = map.isFavorite
    )

    private fun String.formatFileName() =
        if (this.length > MAX_CHAR) this.substring(START_INDEX, MAX_CHAR)
    else this
}
