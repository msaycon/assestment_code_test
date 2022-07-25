package com.example.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import timber.log.Timber


/**
 * Created by msaycon on 23,Jun,2021
 */

/**
 *  This function serve as an observer for internet connection
 */

fun registerNetworkCallbacks(context: Context) {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    cm.registerDefaultNetworkCallback(object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isNetworkConnected = true
            Timber.e("Network connected $isNetworkConnected")
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Timber.e("Network Losing %s", maxMsToLive)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isNetworkConnected = false
            Timber.e("Network connected $isNetworkConnected")
        }
    })
}