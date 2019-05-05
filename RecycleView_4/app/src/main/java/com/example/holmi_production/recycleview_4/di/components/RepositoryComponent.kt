package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.di.modules.RepositoryModule
import com.example.holmi_production.recycleview_4.ui.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( dependencies = [NetComponent::class,RoomComponent::class],modules = [RepositoryModule::class])
interface RepositoryComponent{
    fun inject(listFragment: ListFragment)
}