package com.example.myapplication.base

import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.utils.registerNetworkCallbacks
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

/**
 * Created by msaycon on 17,Jul,2022
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        registerNetworkCallbacks(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(
            this
        )
}