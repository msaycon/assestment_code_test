package com.example.myapplication.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.utils.createProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by msaycon on 17,Jul,2022
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    lateinit var context: Context

    var subscriptionsWhileInMemory: CompositeDisposable = CompositeDisposable()

    private var progressDialog: AlertDialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    fun showLoadingProgress(isLoading: Boolean) {
        if (isLoading) {
            progressDialog = createProgressDialog()
            progressDialog?.show()
        } else {
            progressDialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
        }
    }

    open fun initializedViews() {}

    open fun initializedListeners() {}

}