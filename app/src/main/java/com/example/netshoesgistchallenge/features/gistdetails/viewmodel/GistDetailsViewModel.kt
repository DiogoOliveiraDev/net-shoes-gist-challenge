package com.example.netshoesgistchallenge.features.gistdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netshoesgistchallenge.features.gistdetails.state.GistDetailsState
import com.example.netshoesgistchallenge.features.gistdetails.state.GistDetailsStateCreator
import com.example.netshoesgistchallenge.global.CoroutineScopeProvider
import com.example.netshoesgistchallenge.service.repositories.filecontent.FileContentRepository
import com.example.netshoesgistchallenge.service.repositories.gists.GistsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class GistDetailsViewModel(
    private val fileContentRepository: FileContentRepository,
    private val gistsRepository: GistsRepository,
    private val gistDetailsStateCreator: GistDetailsStateCreator,
    override val dispatcher: CoroutineDispatcher
) : CoroutineScopeProvider() {

    val gistDetailsStream: MutableLiveData<GistDetailsState> = MutableLiveData()

    @Suppress("TooGenericExceptionCaught")
    fun getFileContentById(gistId: String) {
        viewModelScope.launch(dispatcher) {
            try {
                gistsRepository.getGistById(gistId).apply {
                    gistDetailsStream.postValue(
                        gistDetailsStateCreator.create(
                            this,
                            fileContentRepository.getContentOfGist(
                                gistId
                            )
                        )
                    )
                }
            } catch (exception: Exception) {
                gistDetailsStream.postValue(
                    gistDetailsStateCreator.createError()
                )
            }
        }
    }
}
