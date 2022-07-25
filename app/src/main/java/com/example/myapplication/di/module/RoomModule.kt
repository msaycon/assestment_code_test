package com.example.myapplication.di.module

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by msaycon on 17,Jul,2022
 */

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}