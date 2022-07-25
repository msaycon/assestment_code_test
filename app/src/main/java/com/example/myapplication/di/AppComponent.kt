package com.example.myapplication.di

import com.example.myapplication.di.viewmodel.ViewModelModule
import com.example.myapplication.base.BaseApplication
import com.example.myapplication.di.module.*
import com.example.myapplication.di.module.ActivityBuilderModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by msaycon on 17,Jul,2022
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        DispatcherModule::class,
        AppModule::class,
        DataModule::class,
        ViewModelModule::class,
        RoomModule::class,
        NetworkModule::class]
)

interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<BaseApplication>
}