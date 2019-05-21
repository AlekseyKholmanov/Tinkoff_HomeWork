package com.example.holmi_production.recycleview_4

import android.app.Application
import com.example.holmi_production.recycleview_4.di.components.*
import com.example.holmi_production.recycleview_4.di.modules.ApplicationModule
import com.example.holmi_production.recycleview_4.di.modules.ContextModule
import com.example.holmi_production.recycleview_4.di.modules.NetModule

class App : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    private val BASE_URL = "https://api.tinkoff.ru/v1/"

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .contextModule(ContextModule(this))
            .netModule(NetModule(BASE_URL))
            .build()
    }

}