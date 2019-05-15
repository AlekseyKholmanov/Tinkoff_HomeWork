package com.example.holmi_production.recycleview_4

import android.app.Application
import com.example.holmi_production.recycleview_4.di.components.*
import com.example.holmi_production.recycleview_4.di.modules.*

class App : Application() {

    companion object {
        lateinit var mPresenterComponent: PresenterComponent
        lateinit var mAppComponent: AppComponent
    }

    private val BASE_URL = "https://api.tinkoff.ru/v1/"

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        mAppComponent = DaggerAppComponent.builder()
            .roomModule(RoomModule(this))
            .connectivityManagerModule((ConnectivityManagerModule(this)))
            .netModule(NetModule(BASE_URL, this))
            .build()

        mPresenterComponent = DaggerPresenterComponent.builder()
            .appComponent(mAppComponent)
            .presenterModule(PresenterModule())
            .build()
    }

}