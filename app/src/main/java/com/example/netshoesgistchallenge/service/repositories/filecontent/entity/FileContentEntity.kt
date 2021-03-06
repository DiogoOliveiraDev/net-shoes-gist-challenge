package com.example.netshoesgistchallenge.service.repositories.filecontent.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file_content_entity")
data class FileContentEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "file_content_table_id")
        val fileContentTableId: Int = 0,

        @ColumnInfo(name ="gist_id")
        val gistId: String,

        @ColumnInfo(name ="content")
        val content: String
)
