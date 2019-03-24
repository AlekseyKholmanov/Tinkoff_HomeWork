package com.example.holmi_production.recycleview_4

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.holmi_production.recycleview_4.db.NewsDatabase
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News

public class NewsViewModel(application: Application):AndroidViewModel(application){
    private val repository:NewsRepository
    private val allNews:MutableLiveData<List<News>>
    init {
        val newsDao = NewsDatabase.getInstance(application)?.newsDao()
        repository = NewsRepository(newsDao)
        allNews = repository.getAllNews()
    }
    fun insert(news: News){
        repository.insert(news)
    }
    fun getAllNews(): MutableLiveData<List<News>> {
        return allNews
    }

}