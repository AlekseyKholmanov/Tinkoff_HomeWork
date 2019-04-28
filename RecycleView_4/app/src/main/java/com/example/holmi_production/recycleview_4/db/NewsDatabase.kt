package com.example.holmi_production.recycleview_4.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.holmi_production.recycleview_4.db.dao.FavoriteDao
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News

@Database(entities = [News::class, FavoriteNews::class], version = 4, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun favoriteNewsDao(): FavoriteNewsDao
    abstract fun favorite(): FavoriteDao


    companion object {

        private val DATABASE_NAME = "basic-sample-db"
        var sInstance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase? {
            if (sInstance == null) {
                synchronized(NewsDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            NewsDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return sInstance
        }
    }

}
