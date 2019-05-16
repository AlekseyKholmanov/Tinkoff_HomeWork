package com.example.holmi_production.recycleview_4.di.components

import com.example.holmi_production.recycleview_4.detail.NewsDetailPresenter
import com.example.holmi_production.recycleview_4.di.modules.ApplicationModule
import com.example.holmi_production.recycleview_4.di.modules.ContextModule
import com.example.holmi_production.recycleview_4.di.modules.NetModule
import com.example.holmi_production.recycleview_4.list.NewsListPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class, NetModule::class, ApplicationModule::class])
interface ApplicationComponent{

    fun getNewsListPresenter(): NewsListPresenter

    fun getNewsItemDetailsPresenter(): NewsDetailPresenter
}