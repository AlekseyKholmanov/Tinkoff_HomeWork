package com.example.holmi_production.recycleview_4.db

import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.News


public class NewsRepository(application: NewsDao?) {

    private val db: NewsDatabase = NewsDatabase.getInstance(application)!!
    private val newsDao:NewsDao
    private val allNews: MutableLiveData<List<News>>

    init {
        newsDao = db.newsDao()
        allNews = newsDao.getAll()
    }

    fun insert(news: News) {
        InsertNewsAsyncTask(newsDao).execute(news)
    }

    fun deleteAll(){
        DeleteAllNewsAsyncTask(newsDao).execute()
    }

    fun getAllNews(): MutableLiveData<List<News>> {
        return allNews
    }

    private class InsertNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<News, Void, Void>() {
        override fun doInBackground(vararg params: News?): Void? {
            newsDao.insert(params[0]!!)
            return null
        }
    }
    private class DeleteAllNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            newsDao.deleteAll()
            return null
        }
    }


}