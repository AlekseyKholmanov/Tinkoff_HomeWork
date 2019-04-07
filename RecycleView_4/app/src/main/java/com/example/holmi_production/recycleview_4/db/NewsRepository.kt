package com.example.holmi_production.recycleview_4.db

import android.content.Context
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Single


public class NewsRepository(context: Context) {


    private val db: NewsDatabase = NewsDatabase.getInstance(context)!!
    private val newsDao: NewsDao
    private val favoriteNewsDao: FavoriteNewsDao
    interface UpdateFavorite{
        fun onUpdateData()
    }
    private var callback:UpdateFavorite?  = null

    fun setOnCallbackListener(callback: UpdateFavorite){
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
        callback!!.onUpdateData()
    }

    fun deleteFavotiteNews(newsId: Int) {
        DeleteFavoriteNewsAsynkTask(favoriteNewsDao).execute(newsId)
        callback!!.onUpdateData()
    }

    fun deleteAll() {
        DeleteAllNewsAsyncTask(newsDao).execute()
    }

    fun getAllNews(): Single<List<News>> {
        return newsDao.getAll()
    }

    fun getAllFavoriteNews(ids:Array<Int>):Single<List<News>>{
        return newsDao.getNewsByIds(ids)
    }

    fun getAllFavoriteIds(): Single<Array<Int>> {
        return favoriteNewsDao.getAllFavoriteIds()
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
}