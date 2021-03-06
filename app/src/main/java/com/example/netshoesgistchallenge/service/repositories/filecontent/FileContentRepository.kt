package com.example.netshoesgistchallenge.service.repositories.filecontent

import com.example.netshoesgistchallenge.common.Constants
import com.example.netshoesgistchallenge.common.Constants.NO_CONTENT_FOUND
import com.example.netshoesgistchallenge.database.AppDatabase
import com.example.netshoesgistchallenge.service.apis.FileContentService
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity
import okhttp3.ResponseBody

class FileContentRepository(
        private val database: AppDatabase,
        private val api: FileContentService
) {

    suspend fun getContentOfGist(gistId: String) : String {
        getContentFromLocal(gistId).let {
            return it?.content ?: getContentFromRemote(getFileUrlFromGist(gistId)).let { responseBody ->
                return getResponseBody(responseBody).apply {
                    saveFileContentOnDatabase(
                        FileContentEntity(
                            gistId = gistId,
                            content = this
                        )
                    )
                }
            }
        }
    }

    private fun getFileUrlFromGist(gistId: String) =
        database.gistsDao().getById(gistId)?.files?.fileEmbedded?.contentUrl ?: NO_CONTENT_FOUND

    private fun getContentFromLocal(gistId: String) =
            database.gistContentDao().getById(gistId)

    private suspend fun getContentFromRemote(gistId: String) =
            api.getContent(gistId)

    private fun saveFileContentOnDatabase(input: FileContentEntity) =
            database.gistContentDao().insert(input)

    private fun getResponseBody(responseBody: ResponseBody) =
            try {
                responseBody.string()
            } catch (outOfMemoryException: OutOfMemoryError) {
                Constants.TOO_LARGE_CONTENT
            }
}
