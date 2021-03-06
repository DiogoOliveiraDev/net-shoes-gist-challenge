package com.example.netshoesgistchallenge.features.home.state

import com.example.netshoesgistchallenge.features.home.adapter.GistItem
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap

class HomeStateCreator {

    fun showError(isErrorFetchMoreItems: Boolean) =
        HomeState(
            errorLayoutVisible = isErrorFetchMoreItems.not(),
            isLoading = false,
            thereIsNotMoreResults = isErrorFetchMoreItems
        )

    fun manageLoading(isRefreshing: Boolean, showShimmer: Boolean) =
        HomeState(
            shimmerVisibility = showShimmer,
            errorLayoutVisible = false,
            isLoading = isRefreshing,
            thereIsNotMoreResults = false
        )

    fun createGistItems(map: List<GistMap>) =
            map.map {
                createGistItem(it)
            }.toList()

    private fun createGistItem(map: GistMap) =
            GistItem(
                    gistId = map.gistId,
                    user = map.owner.login,
                    avatarUrl = map.owner.avatarUrl,
                    contentType = map.files.file.type ,
                    fileTitle = map.files.file.fileName,
                    language = map.files.file.language,
                    isFavorite = map.isFavorite
            )
}
