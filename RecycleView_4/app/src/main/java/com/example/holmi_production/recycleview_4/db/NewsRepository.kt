package com.example.holmi_production.recycleview_4.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News


public class NewsRepository(application: Application) {

    private val db: NewsDatabase = NewsDatabase.getInstance(application)!!
    private val newsDao:NewsDao
    private  val favoriteNewsDao:FavoriteNewsDao
    private val allNews: LiveData<List<News>>
    private val allFavoriteNews: LiveData<List<FavoriteNews>>

    init {
        newsDao = db.newsDao()
        favoriteNewsDao = db.favoriteNewsDao()
        allNews = newsDao.getAll()
        allFavoriteNews = favoriteNewsDao.getAll()
    }

    fun insert(news: News) {
        InsertNewsAsyncTask(newsDao).execute(news)
    }

    fun deleteAll(){
        DeleteAllNewsAsyncTask(newsDao).execute()
    }

    fun getAllNews(): LiveData<List<News>> {
        return allNews
    }

    fun getAllFavoriteNews(): LiveData<List<FavoriteNews>> {
        return allFavoriteNews
    }

    fun getNewsById(id:Int):News{
        return allNews.let { t ->
            t.value!!.first { t->t.id==id }
        }
    }
    private class InsertNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<News, Void, Void>() {
        override fun doInBackground(vararg params: News): Void? {
            newsDao.insert(params[0])
            return null
        }
    }
    private class DeleteAllNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void?{
            newsDao.deleteAll()
            return null
        }
    }


}