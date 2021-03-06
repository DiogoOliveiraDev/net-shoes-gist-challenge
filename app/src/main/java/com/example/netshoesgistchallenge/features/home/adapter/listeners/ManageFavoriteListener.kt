package com.example.netshoesgistchallenge.features.home.adapter.listeners

interface ManageFavoriteListener {
    fun onChangeFavoriteStatus(gistId: String, isAddToFavorites: Boolean)
}
