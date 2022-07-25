package com.example.myapplication.di.module

import com.example.myapplication.di.scope.PerActivity
import com.example.myapplication.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by msaycon on 17,Jul,2022
 */

@Module
internal abstract class ActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}