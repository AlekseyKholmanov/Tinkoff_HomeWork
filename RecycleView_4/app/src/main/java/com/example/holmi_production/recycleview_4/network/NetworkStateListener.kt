package com.example.holmi_production.recycleview_4.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkStateListener @Inject constructor(
    private val context: Context,
    private val cm:ConnectivityManager
    ){

}