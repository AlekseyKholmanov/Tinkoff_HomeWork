package com.example.holmi_production.recycleview_4.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.holmi_production.recycleview_4.source.db.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {
    private val mContext = context
    private val DATABASE_NAME = "basic-sample-db"
    @Provides
    @Singleton
    fun provideRoomDataSource(): NewsDatabase {
        return Room.databaseBuilder(
            mContext,
            NewsDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}