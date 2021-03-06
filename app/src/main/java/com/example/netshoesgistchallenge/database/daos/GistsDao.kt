package com.example.netshoesgistchallenge.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.netshoesgistchallenge.service.repositories.gists.entity.GistEntity

@Dao
interface GistsDao {

    @Query("SELECT * FROM gists_by_user_entity")
    fun getAll(): List<GistEntity>

    @Query("SELECT * FROM gists_by_user_entity WHERE login LIKE :user")
    fun getAllByUser(user: String): List<GistEntity>

    @Query("SELECT * FROM gists_by_user_entity WHERE gist_id LIKE :gistId")
    fun getById(gistId: String): GistEntity?

    @Query("SELECT * FROM gists_by_user_entity WHERE gist_id LIKE :gistIds")
    fun getAllById(gistIds: List<String>): List<GistEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(gists: List<GistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gists: GistEntity)

    @Delete
    fun deleteMany(gists: List<GistEntity>)

    @Query("DELETE FROM gists_by_user_entity WHERE gist_id LIKE :gistId")
    fun deleteById(gistId: String)

    @Query("DELETE FROM gists_by_user_entity WHERE is_favorite = 0")
    fun clear()
}
