package com.example.holmi_production.recycleview_4.db

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.News


public class NewsRepository(private val newsDao: NewsDao) {

    val allNews: LiveData<List<News>> = newsDao.getAll()

    @WorkerThread
    suspend fun insert(news: News) {
        newsDao.insert(news)
    }
}