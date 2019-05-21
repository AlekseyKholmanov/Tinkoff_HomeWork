package com.example.holmi_production.recycleview_4.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.Observable
import javax.inject.Inject

class NetworkStateListener @Inject constructor(
    private val context: Context,
    private val cm: ConnectivityManager
) {

    private val stateObservable = Observable.create<Boolean> { emitter ->
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                emitter.onNext(isInternetConnected())
            }
        }
        context.registerReceiver(receiver,filter)
        emitter.setCancellable{context.unregisterReceiver(receiver)}
    }

    fun observeNewtworkState():Observable<Boolean> = stateObservable


    private fun isInternetConnected(): Boolean = cm.activeNetworkInfo?.isConnected == true
}