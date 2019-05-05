package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.Model.RemoteDataSource
import com.example.holmi_production.recycleview_4.di.modules.AppModule
import com.example.holmi_production.recycleview_4.di.modules.ContextModule
import com.example.holmi_production.recycleview_4.di.modules.NetModule
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [NetModule::class,AppModule::class])
interface NetComponent{
    fun retrofit(): Retrofit
    fun okHttpClient(): OkHttpClient
    fun remoteDataSource():RemoteDataSource
}