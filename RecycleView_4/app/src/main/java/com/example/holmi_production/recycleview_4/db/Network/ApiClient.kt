package com.example.holmi_production.recycleview_4.db.Network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient{
    var retrofit: Retrofit? = null
    var okHttpClient: OkHttpClient? = null
    val REQUEST_TIMEOUT = 40
    val BASE_URL = "https://api.tinkoff.ru/v1/"

    fun getClient(context:Context): Retrofit {
        if(retrofit == null)
            retrofit= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit!!
    }
}