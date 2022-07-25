package com.example.myapplication.listeners

import android.view.View

/**
 * Created by msaycon on 18,Jul,2022
 */

interface AdapterEventListener {

    fun onItemSelected(view: View, item: Any?, position: Int)

    fun onItemLongClick(item: Any?, position: Int)
}