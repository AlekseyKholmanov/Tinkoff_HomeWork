package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.di.modules.*
import com.example.holmi_production.recycleview_4.source.network.RemoteDataSource
import com.example.holmi_production.recycleview_4.source.db.NewsDatabase
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, NetModule::class, AppModule::class, RepositoryModule::class])
interface AppComponent{
    fun remoteDataSource(): RemoteDataSource
    fun newsDatabase(): NewsDatabase
    fun newsRepository():NewsRepository
}