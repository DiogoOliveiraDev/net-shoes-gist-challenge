package com.example.netshoesgistchallenge.service.apis

import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GistsService {

    @GET("users/{user}/gists")
    suspend fun getGistsFromUser(
        @Path("user")user: String,
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int
    ) : List<GistEntity>

    @GET("/gists/{gistid}")
    suspend fun getGistById(@Path("gistid")gistId: String ) : GistEntity

    @GET("/gists")
    suspend fun getNewGists(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("since") since: String,
    ) : List<GistEntity>
}
