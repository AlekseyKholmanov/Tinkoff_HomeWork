package com.example.holmi_production.recycleview_4.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News

@Database(entities = [News::class, FavoriteNews::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    private var sInstance: AppDatabase? = null

    abstract fun productDao(): NewsDao
    abstract fun commentDao(): FavoriteNewsDao

    companion object {

        private val DB_NAME = "news-app-db"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .addCallback(object : Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        })
                        .build()
                }
            }
            return sInstance
        }
    }


}