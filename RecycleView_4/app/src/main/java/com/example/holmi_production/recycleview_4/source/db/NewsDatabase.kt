package com.example.holmi_production.recycleview_4.source.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.holmi_production.recycleview_4.source.db.dao.FavoriteDao
import com.example.holmi_production.recycleview_4.source.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.source.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.source.db.dao.ViewedNewsDao
import com.example.holmi_production.recycleview_4.source.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.source.db.entity.News
import com.example.holmi_production.recycleview_4.source.db.entity.ViewedNews

@Database(entities = [News::class, FavoriteNews::class, ViewedNews::class], version = 6, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun favoriteNewsDao(): FavoriteNewsDao
    abstract fun favorite(): FavoriteDao
    abstract fun viewedNews():ViewedNewsDao
}
