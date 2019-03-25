package com.example.holmi_production.recycleview_4

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News

public class NewsViewModel(application: Application):AndroidViewModel(application){
    private val repository:NewsRepository
    private val allNews: LiveData<List<News>>
    init {
        repository = NewsRepository(application)
        allNews = repository.getAllNews()
    }
    fun insert(news: News){
        repository.insert(news)
    }
    fun getAllNews(): LiveData<List<News>> {
        return allNews
    }

}