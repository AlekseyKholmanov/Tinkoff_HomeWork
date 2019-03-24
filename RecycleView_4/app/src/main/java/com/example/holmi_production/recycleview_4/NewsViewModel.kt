package com.example.holmi_production.recycleview_4

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.holmi_production.recycleview_4.db.NewsDatabase
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    val allNews: LiveData<List<News>>
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val newsDao = NewsDatabase.getInstance(application,scope)!!.newsDao()
        repository = NewsRepository(newsDao)
        allNews = repository.allNews
    }

    fun insert(news: News) = scope.launch(Dispatchers.IO) {
        repository.insert(news)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}