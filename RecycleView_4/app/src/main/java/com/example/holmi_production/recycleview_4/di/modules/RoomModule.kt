package com.example.holmi_production.recycleview_4.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.holmi_production.recycleview_4.db.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {
    private val DATABASE_NAME = "basic-sample-db"
    @Provides
    @Singleton
    fun provideRoomDataSource(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}