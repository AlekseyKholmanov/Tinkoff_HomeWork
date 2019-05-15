package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.di.modules.PresenterModule
import com.example.holmi_production.recycleview_4.di.scope.PresenterScope
import com.example.holmi_production.recycleview_4.TypeElement.NewsFragmentPresenterImp
import com.example.holmi_production.recycleview_4.detail.SingleNewsPresenterImp
import dagger.Component

@PresenterScope
@Component(modules = [PresenterModule::class], dependencies = [AppComponent::class])
interface PresenterComponent{
    fun listPresenter(): NewsFragmentPresenterImp
    fun singlePresenter(): SingleNewsPresenterImp
}