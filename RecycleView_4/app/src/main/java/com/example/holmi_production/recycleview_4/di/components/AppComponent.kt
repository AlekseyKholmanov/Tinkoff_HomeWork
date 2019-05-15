package com.example.holmi_production.recycleview_4.di.components

import android.net.ConnectivityManager
import com.example.holmi_production.recycleview_4.di.modules.*
import com.example.holmi_production.recycleview_4.api.RemoteDataSource
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import com.example.holmi_production.recycleview_4.model.NewsRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, NetModule::class, AppModule::class, RepositoryModule::class, ConnectivityManagerModule::class])
interface AppComponent{
    fun remoteDataSource(): RemoteDataSource
    fun newsDatabase(): NewsDatabase
    fun newsRepository():NewsRepository
    fun connectivityManager():ConnectivityManager
}