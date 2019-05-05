package com.example.holmi_production.recycleview_4.di

import android.app.Application
import com.example.holmi_production.recycleview_4.di.components.*
import com.example.holmi_production.recycleview_4.di.modules.*

class App : Application() {

    companion object {
        lateinit var mRepositoryComponent: RepositoryComponent
        lateinit var mRoomComponent: RoomComponent
        lateinit var mNetComponent: NetComponent
    }

    private val BASE_URL = "https://api.tinkoff.ru/v1/"

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        mRoomComponent = DaggerRoomComponent.builder()
            .roomModule(RoomModule(this))
            .build()
        mNetComponent = DaggerNetComponent.builder()
            .netModule(NetModule(BASE_URL, this))
            .build()

        mRepositoryComponent = DaggerRepositoryComponent.builder()
            .netComponent(mNetComponent)
            .roomComponent(mRoomComponent)
            .repositoryModule(RepositoryModule())
            .build()
    }

    fun getRepositoryComponent():RepositoryComponent{
        return mRepositoryComponent
    }

}