package com.example.myapplication.di.module

import android.content.Context
import com.example.myapplication.base.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by msaycon on 17,Jul,2022
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: BaseApplication): Context = application.applicationContext
}