package com.example.holmi_production.recycleview_4

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.holmi_production.recycleview_4.Model.FavoriteNews
import com.example.holmi_production.recycleview_4.Model.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.Model.NewsDao


@Database(entities = [News::class, FavoriteNews::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun favoriteNewsDao():FavoriteNewsDao

    companion object {
        private const val DB_NAME = "MY_DB"
        var INSTANCE: NewsDatabase? = null

        fun getIncstance (context: Context):NewsDatabase?{
            if (INSTANCE == null){
                synchronized(NewsDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, DB_NAME).build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}