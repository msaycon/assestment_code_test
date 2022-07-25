package com.example.myapplication.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.local.entities.MediaEntity

/**
 * Created by msaycon on 17,Jul,2022
 */

/**
 * This is interface for Room queries and transactions
 */

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mediaList: List<MediaEntity>)

    @Query("SELECT * FROM media ORDER BY trackName")
    fun getAll(): PagingSource<Int, MediaEntity>

    @Query("DELETE FROM media")
    suspend fun clearAll()
}