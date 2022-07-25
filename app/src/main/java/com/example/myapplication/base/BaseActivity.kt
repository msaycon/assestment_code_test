package com.example.myapplication.base

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by msaycon on 17,Jul,2022
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    open fun initializedViews() {}

    open fun initializedListeners() {}

}