package com.example.myapplication.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.di.scope.ActivityContext
import com.example.myapplication.di.scope.PerActivity
import com.example.myapplication.ui.activities.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by msaycon on 17,Jul,2022
 */

@Module
internal abstract class ActivityBuilderModule {

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun activityContext(activity: AppCompatActivity): Context

    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}