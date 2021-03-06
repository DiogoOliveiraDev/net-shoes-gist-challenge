package com.example.netshoesgistchallenge.service.repositories

import java.lang.Exception

class CouldNotRetrieveDataException(message: String) : Exception(message) {
    companion object {
        const val COULD_NOT_RETRIEVE_DATA = "Could not retrieve data"
    }
}

