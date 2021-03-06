package com.example.netshoesgistchallenge.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.netshoesgistchallenge.service.repositories.filecontent.entity.FileContentEntity

@Dao
interface GistContentDao {

    @Query("SELECT * FROM file_content_entity")
    fun getAll(): List<FileContentEntity>

    @Query("SELECT * FROM file_content_entity WHERE gist_id LIKE :gistId")
    fun getById(gistId: String?): FileContentEntity?

    @Query("SELECT * FROM file_content_entity WHERE gist_id LIKE :gistIds")
    fun getAllById(gistIds: List<String>): List<FileContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gists: List<FileContentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gists: FileContentEntity)

    @Delete
    fun deleteAll(gists: List<FileContentEntity>)

    @Query("DELETE FROM file_content_entity WHERE gist_id LIKE :gistId")
    fun deleteById(gistId: String)

    @Query("DELETE FROM file_content_entity WHERE gist_id LIKE :gistIds")
    fun deleteManyById(gistIds: List<String>)
}
