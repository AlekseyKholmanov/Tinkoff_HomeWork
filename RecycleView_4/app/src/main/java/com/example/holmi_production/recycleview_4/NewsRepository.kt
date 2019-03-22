package com.example.holmi_production.recycleview_4

import com.example.holmi_production.recycleview_4.Model.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.Model.NewsDao

public class NewsRepository{

    private var newsDao:NewsDao? = null
    private var favoriteNewsDao:FavoriteNewsDao? = null
    private var allNews:List<News> = listOf()

    init {
        val db = NewsDatabase.INSTANCE
        favoriteNewsDao = db?.favoriteNewsDao()
        newsDao = db?.newsDao()
        allNews = newsDao!!.getAll()
    }

    fun getAllNews(): List<News> {
        return allNews
    }

}