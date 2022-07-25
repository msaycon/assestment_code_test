package com.example.myapplication.base

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.activities.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by msaycon on 01,Oct,2019
 */
abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    open fun initializeListeners() {

    }

    open fun initializeViews() {

    }

    open fun setupToolbar(title: String) {
        (requireActivity() as MainActivity).setToolbarTitle(title)
    }
}
