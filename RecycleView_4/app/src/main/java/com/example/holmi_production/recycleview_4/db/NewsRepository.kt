package com.example.holmi_production.recycleview_4.db

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.ListFragment
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News


public class NewsRepository(context: Context) {


    private val db: NewsDatabase = NewsDatabase.getInstance(context)!!
    private val newsDao: NewsDao
    private val favoriteNewsDao: FavoriteNewsDao
    private val allNews: List<News>
    private val allFavoriteNews: List<FavoriteNews>

    init {
        newsDao = db.newsDao()
        favoriteNewsDao = db.favoriteNewsDao()
        allNews = newsDao.getAll()
        allFavoriteNews = favoriteNewsDao.getAll()
    }

    fun insert(news: News) {
        InsertNewsAsyncTask(newsDao).execute(news)
    }

    fun insertFavoriteNews(news: FavoriteNews) {
        InsertFavoriteNewsAsyncTask(favoriteNewsDao).execute(news)
    }

    fun deleteFavotiteNews(newsId:Int) {
        DeleteFavoriteNewsAsynkTask(favoriteNewsDao).execute(newsId)
    }

    fun deleteAll() {
        DeleteAllNewsAsyncTask(newsDao).execute()
    }

    fun getAllNews(): List<News> {
        return allNews
    }

    fun getAllFavoriteNews(): List<FavoriteNews> {
        return allFavoriteNews
    }

    fun getFavoriteNewsById(newsId:Int):FavoriteNews?{
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

    private class GetFavoriteNewsByIdAsyncTaks(val favoriteNewsDao: FavoriteNewsDao) : AsyncTask<Int, Void, FavoriteNews>() {
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
}