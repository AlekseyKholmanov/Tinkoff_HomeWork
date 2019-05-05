package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.db.NewsDatabase
import com.example.holmi_production.recycleview_4.di.modules.ContextModule
import com.example.holmi_production.recycleview_4.di.modules.RoomModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class,ContextModule::class])
interface RoomComponent{
    fun room(): NewsDatabase
}