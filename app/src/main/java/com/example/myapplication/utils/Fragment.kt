package com.example.myapplication.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.base.BaseFragment

/**
 * Created by msaycon on 17,Jul,2022
 */
inline fun <reified T : ViewModel> Fragment.viewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

val BaseFragment.viewContainer: View
    get() {
        return view!!
    }

val BaseFragment.appContext: Context get() = activity?.applicationContext!!