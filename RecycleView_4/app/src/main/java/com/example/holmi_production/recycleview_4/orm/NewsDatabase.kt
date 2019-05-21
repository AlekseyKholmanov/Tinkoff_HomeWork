package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.holmi_production.recycleview_4.model.FavoriteNews
import com.example.holmi_production.recycleview_4.model.NewsItemDetails
import com.example.holmi_production.recycleview_4.model.NewsItemTitle

@Database(entities = [NewsItemTitle::class, FavoriteNews::class, NewsItemDetails::class], version = 12, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun favoriteNewsDao(): FavoriteNewsDao
    abstract fun favorite(): FavoriteDao
    abstract fun viewedNews(): ViewedNewsDao
}
