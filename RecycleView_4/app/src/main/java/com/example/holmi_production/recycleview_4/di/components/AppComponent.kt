package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.Model.RemoteDataSource
import com.example.holmi_production.recycleview_4.db.NewsDatabase
import com.example.holmi_production.recycleview_4.di.modules.AppModule
import com.example.holmi_production.recycleview_4.di.modules.ContextModule
import com.example.holmi_production.recycleview_4.di.modules.NetModule
import com.example.holmi_production.recycleview_4.di.modules.RoomModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, NetModule::class, AppModule::class])
interface AppComponent{
    fun remoteDataSource(): RemoteDataSource
    fun newsDatabase():NewsDatabase
}