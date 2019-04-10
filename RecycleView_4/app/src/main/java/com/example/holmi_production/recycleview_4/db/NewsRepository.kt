package com.example.holmi_production.recycleview_4.db

import android.content.Context
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class NewsRepository(context: Context) {


    private val db: NewsDatabase = NewsDatabase.getInstance(context)!!
    private val newsDao: NewsDao
    private val favoriteNewsDao: FavoriteNewsDao

    interface UpdateFavorite {
        fun onUpdateData()
    }

    private var callback: UpdateFavorite? = null

    fun setOnCallbackListener(callback: UpdateFavorite) {
        this.callback = callback
    }

    init {
        newsDao = db.newsDao()
        favoriteNewsDao = db.favoriteNewsDao()
    }

    fun insertFavoriteNews(news: FavoriteNews):Completable {
        return Completable.fromCallable { favoriteNewsDao.insert(news)}
            .subscribeOn(Schedulers.io())
    }

    fun deleteFavotiteNews(newsId: Int):Completable {
       return Completable.fromCallable { favoriteNewsDao.delete(newsId) }
           .subscribeOn(Schedulers.io())
        //callback!!.onUpdateData()
    }

    fun getAllNews(): Single<List<News>> {
        return newsDao.getAll()
            .subscribeOn(Schedulers.io())
    }

    fun getAllFavoriteNews(ids: Array<Int>): Single<List<News>> {
        return newsDao.getNewsByIds(ids)
            .subscribeOn(Schedulers.io())
    }

    fun getAllFavoriteIds(): Single<Array<Int>> {
        return favoriteNewsDao.getAllFavoriteIds()
            .subscribeOn(Schedulers.io())
    }

    fun getFavoriteNewsById(newsId: Int): Maybe<FavoriteNews> {
        return favoriteNewsDao.getNewsById(newsId)
            .subscribeOn(Schedulers.io())
    }

    fun getNewsById(id: Int): Single<News> {
        return newsDao.getNewsById(id)
            .subscribeOn(Schedulers.io())
    }


}