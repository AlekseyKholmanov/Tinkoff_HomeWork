package com.example.holmi_production.recycleview_4.di.modules

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ConnectivityManagerModule(context: Context) {

    private val mContext = context

    @Provides
    @Singleton
    fun provideCm(): ConnectivityManager {
        return mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}