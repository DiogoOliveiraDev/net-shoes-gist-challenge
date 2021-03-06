package com.example.netshoesgistchallenge.service.apis

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface FileContentService {

    @GET
    suspend fun getContent(@Url contentUrl: String) : ResponseBody
}
