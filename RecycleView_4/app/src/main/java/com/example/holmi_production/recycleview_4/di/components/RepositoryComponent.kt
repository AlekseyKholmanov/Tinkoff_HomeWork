package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.di.Scope.CustomScopeName
import com.example.holmi_production.recycleview_4.di.modules.RepositoryModule
import com.example.holmi_production.recycleview_4.ui.ListFragment
import dagger.Component

@CustomScopeName
@Component( dependencies = [AppComponent::class],modules = [RepositoryModule::class])
interface RepositoryComponent{
    fun inject(listFragment: ListFragment)
}