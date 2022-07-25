package com.example.myapplication.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.di.scope.ActivityContext
import com.example.myapplication.di.scope.PerActivity
import dagger.Binds
import dagger.Module

/**
 * Created by msaycon on 17,Jul,2022
 */

@Module
abstract class BaseActivityModule {

    @Binds
    @PerActivity
    @ActivityContext
    internal abstract fun activityContext(activity: AppCompatActivity): Context
}