package com.example.holmi_production.recycleview_4.db

import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News


public class NewsRepository(context: Context) {


    private val db: NewsDatabase = NewsDatabase.getInstance(context)!!
    private val newsDao: NewsDao
    private val favoriteNewsDao: FavoriteNewsDao
    interface Callback{
        fun onUpdateData(list:List<FavoriteNews>)
    }
    private var callback:Callback?  = null

    fun setOnCallbackListener(callback: Callback){
        this.callback = callback
    }
    init {
        newsDao = db.newsDao()
        favoriteNewsDao = db.favoriteNewsDao()
    }

    fun insert(news: News) {
        InsertNewsAsyncTask(newsDao).execute(news)
    }

    fun insertFavoriteNews(news: FavoriteNews) {
        InsertFavoriteNewsAsyncTask(favoriteNewsDao).execute(news)
    }

    fun deleteFavotiteNews(newsId: Int) {
        DeleteFavoriteNewsAsynkTask(favoriteNewsDao).execute(newsId)
    }

    fun deleteAll() {
        DeleteAllNewsAsyncTask(newsDao).execute()
    }

    fun getAllNews(): List<News> {
        return GetAllNewsAsyncTask(newsDao).execute().get()
    }

    fun getAllFavoriteNews(): List<FavoriteNews> {
        return GetAllFavoriteNewsAsyncTask(favoriteNewsDao).execute().get()
    }

    fun getFavoriteNewsById(newsId: Int): FavoriteNews? {
        return GetFavoriteNewsByIdAsyncTaks(favoriteNewsDao).execute(newsId).get()
    }

    fun getNewsById(id: Int): News {
        return GetNewsByIdAsyncTaks(newsDao).execute(id).get()
    }


    private class DeleteFavoriteNewsAsynkTask(val favoriteNewsDao: FavoriteNewsDao) :
        AsyncTask<Int, Void, Void>() {
        override fun doInBackground(vararg params: Int?): Void? {
            favoriteNewsDao.delete(params[0]!!)
            return null
        }

    }

    private class GetNewsByIdAsyncTaks(val newsDao: NewsDao) : AsyncTask<Int, Void, News>() {
        override fun doInBackground(vararg params: Int?): News {
            return newsDao.getNewsById(params[0]!!)
        }
    }

    private class GetFavoriteNewsByIdAsyncTaks(val favoriteNewsDao: FavoriteNewsDao) :
        AsyncTask<Int, Void, FavoriteNews>() {
        override fun doInBackground(vararg params: Int?): FavoriteNews? {
            return favoriteNewsDao.getNewsById(params[0]!!)
        }
    }

    private class InsertNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<News, Void, Void>() {
        override fun doInBackground(vararg params: News): Void? {
            newsDao.insert(params[0])
            return null
        }
    }

    private class DeleteAllNewsAsyncTask(val newsDao: NewsDao) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            newsDao.deleteAll()
            return null
        }
    }

    private class InsertFavoriteNewsAsyncTask(val favoriteNewsDao: FavoriteNewsDao) :
        AsyncTask<FavoriteNews, Void, Void>() {
        override fun doInBackground(vararg params: FavoriteNews?): Void? {
            favoriteNewsDao.insert(params[0]!!)
            return null
        }
    }
    private class GetAllNewsAsyncTask(val newsDao: NewsDao) :
        AsyncTask<Void, Void, List<News>>() {
        override fun doInBackground(vararg params: Void?): List<News> {
            return newsDao.getAll()
        }
    }
    private class GetAllFavoriteNewsAsyncTask(val favoriteNewsDao: FavoriteNewsDao) :
        AsyncTask<Void, Void, List<FavoriteNews>>() {
        override fun doInBackground(vararg params: Void?): List<FavoriteNews> {
            return favoriteNewsDao.getAll()
        }
    }
}