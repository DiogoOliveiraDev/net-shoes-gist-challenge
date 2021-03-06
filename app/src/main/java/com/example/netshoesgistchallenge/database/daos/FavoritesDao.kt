package com.example.netshoesgistchallenge.database.daos


import androidx.room.Dao
import androidx.room.Query
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM gists_by_user_entity WHERE is_favorite = 1")
    fun getAll(): List<GistEntity>

    @Query("UPDATE gists_by_user_entity SET is_favorite = 1 WHERE gist_id LIKE :gistId")
    fun add(gistId: String)

    @Query("UPDATE gists_by_user_entity SET is_favorite = 0 WHERE gist_id LIKE :gistId")
    fun remove(gistId: String)

    @Query("DELETE FROM gists_by_user_entity WHERE is_favorite = 0")
    fun clearAll()
}
