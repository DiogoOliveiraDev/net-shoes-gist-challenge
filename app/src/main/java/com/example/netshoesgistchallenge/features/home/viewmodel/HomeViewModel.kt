package com.example.netshoesgistchallenge.features.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.netshoesgistchallenge.features.home.adapter.GistItem
import com.example.netshoesgistchallenge.features.home.adapter.listeners.ManageFavoriteListener
import com.example.netshoesgistchallenge.features.home.state.HomeState
import com.example.netshoesgistchallenge.features.home.state.HomeStateCreator
import com.example.netshoesgistchallenge.global.CoroutineScopeProvider
import com.example.netshoesgistchallenge.service.repositories.RepositoryUtils.nowISOAsString
import com.example.netshoesgistchallenge.service.repositories.favorites.FavoritesRepository
import com.example.netshoesgistchallenge.service.repositories.gists.GistsRepository
import com.example.netshoesgistchallenge.service.repositories.gists.map.GistMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions", "TooGenericExceptionCaught")

class HomeViewModel(
    private val gistsRepository: GistsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val stateCreator: HomeStateCreator,
    override val dispatcher: CoroutineDispatcher
) : CoroutineScopeProvider(), ManageFavoriteListener {

    companion object{
        const val INITIAL_PAGE = 1
    }

    private val currentGistsList = mutableListOf<GistMap>()
    val homeStateStream: MutableLiveData<HomeState> = MutableLiveData()
    val gistItemListStream: MutableLiveData<List<GistItem>> = MutableLiveData()
    val favoritesItemListStream: MutableLiveData<List<GistItem>> = MutableLiveData()
    private var lastUserSearched = ""
    private var since = nowISOAsString()
    private var page = INITIAL_PAGE
    private var currentSearchMode = GistSearchMode.NEW_GISTS
        set(value) {
            if (field == value) return
            resetSearch()
            field = value
        }
    private var thereIsNoMoreResults = false

    fun getMoreGits() {
        page++
        getGists(currentSearchMode)
    }

    private fun resetSearch() {
        since = nowISOAsString()
        page = INITIAL_PAGE
        currentGistsList.clear()
        thereIsNoMoreResults = false
    }

    fun getGists(gistSearchMode: GistSearchMode, user: String = "") {
        this.currentSearchMode = gistSearchMode

        if (user.isNotEmpty()) {
            lastUserSearched = user
        }

        if (thereIsNoMoreResults)
            return

        viewModelScope.launch(dispatcher) {
            if (gistSearchMode == GistSearchMode.NEW_GISTS) getNewGists()
            else getGistsFromUser()
        }
    }

    private suspend fun getNewGists() {
        postRefreshingState(true, page == 1)
        try {
            updateCurrentGistsList(
                gistsRepository.getNewGists(
                    page,
                    since
                )
            )
            postRefreshingState(false)
        } catch (exception: Exception) {
            thereIsNoMoreResults = currentGistsList.isNotEmpty()
            postError(thereIsNoMoreResults)
        }
    }

    fun getFavorites() {
        viewModelScope.launch(dispatcher) {
            try {
                postAllFavorites()
            } catch (exception: java.lang.Exception) {
                postError()
            }
        }
    }

    private suspend fun getGistsFromUser() {
        try {
            postRefreshingState(true, page == 1)
            gistsRepository.getAllDataByUser(
                lastUserSearched,
                page
            ).apply {
                if (isEmpty()) {
                    postErrorCheckNoMoreResults()
                } else {
                    currentGistsList.addAll(
                        this
                    )
                    postCurrentGistList()
                    postRefreshingState(false)
                }
            }
        } catch (exception: Exception) {
            postErrorCheckNoMoreResults()
        }
    }

    override fun onChangeFavoriteStatus(gistId: String, isAddToFavorites: Boolean) {
        viewModelScope.launch(dispatcher) {
            try {
                manageFavoritesGists(gistId, isAddToFavorites)
                updateCurrentGistsFavorites(gistId, isAddToFavorites)
            } catch (exception: Exception) {
                postError(true)
            }
        }
    }

    private suspend fun manageFavoritesGists(gistId: String, addToFavorites: Boolean) {
        if (addToFavorites) {
            favoritesRepository.addFavorite(gistId)
        } else {
            favoritesRepository.removeFavorite(gistId)
        }

        postAllFavorites()
    }

    private fun updateCurrentGistsFavorites(gistId: String, addToFavorites: Boolean) {
        var i = 0
        while (i < currentGistsList.size) {
            if (currentGistsList[i].gistId == gistId) {
                currentGistsList[i] = currentGistsList[i].copy(isFavorite = addToFavorites)
                postCurrentGistList()
                break
            }

            i++
        }
    }

    private fun updateCurrentGistsList(newGistList: List<GistMap>) {
        currentGistsList.addAll(newGistList)

        postCurrentGistList()
    }

    fun onSwipeRefresh(refreshing: Boolean) {
        if (refreshing) {
            postRefreshingState(refreshing, true)
            resetSearch()
            getGists(GistSearchMode.NEW_GISTS)
        }
    }

    private fun postCurrentGistList() =
        gistItemListStream.postValue(
            stateCreator.createGistItems(
                currentGistsList
            )
        )

    private fun postAllFavorites() =
        favoritesItemListStream.postValue(
            stateCreator.createGistItems(
                favoritesRepository.getAllFavorites()
            )
        )

    private fun postRefreshingState(isRefreshing: Boolean, showShimmer: Boolean = false) =
        homeStateStream.postValue(
            stateCreator.manageLoading(isRefreshing, showShimmer)
        )

    private fun postErrorCheckNoMoreResults() {
        thereIsNoMoreResults = currentGistsList.isNotEmpty()
        postError(thereIsNoMoreResults)
    }

    private fun postError(isErrorFetchMoreItems: Boolean = false) {
        homeStateStream.postValue(
            stateCreator.showError(isErrorFetchMoreItems)
        )
    }

    fun filterCurrentUserOnList(user: String) {
        currentGistsList.filter {
            it.owner.login == user
        }.apply {
            currentGistsList.clear()
            currentGistsList.addAll(this)
        }

        postCurrentGistList()
    }
}
