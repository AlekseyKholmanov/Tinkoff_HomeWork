package com.example.holmi_production.recycleview_4

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.Model.NewsDao


@Database(entities = [News::class] , version = 1)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun  newsDao(): NewsDao

    companion object {
        var INSTANCE:AppDatabase? = null
    }

    fun getAppDataBase(context: Context): AppDatabase? {
        if (INSTANCE == null){
            synchronized(AppDatabase::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
            }
        }
        return INSTANCE
    }

    fun destroyDataBase(){
        INSTANCE = null
    }
}