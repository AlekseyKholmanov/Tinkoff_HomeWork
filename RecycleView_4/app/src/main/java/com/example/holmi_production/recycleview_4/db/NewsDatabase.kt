package com.example.holmi_production.recycleview_4.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
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
}
