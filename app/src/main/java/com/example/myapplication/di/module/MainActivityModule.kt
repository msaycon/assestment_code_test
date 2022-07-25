package com.example.myapplication.di.module

import com.example.myapplication.ui.fragments.details.DetailFragment
import com.example.myapplication.ui.fragments.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by msaycon on 17,Jul,2022
 */
@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun detailFragment(): DetailFragment
}