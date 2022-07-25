package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.MediaDao
import com.example.myapplication.data.local.entities.MediaEntity

/**
 * Created by msaycon on 17,Aug,2021
 */

/**
 * This are room database settings and configurations
 */

@Database(
    entities = [MediaEntity::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "assesment_db"
    }

    abstract fun mediaDao(): MediaDao
}