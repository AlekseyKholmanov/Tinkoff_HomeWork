package com.example.holmi_production.recycleview_4.db

import android.content.Context
import com.example.holmi_production.recycleview_4.db.Network.ApiClient
import com.example.holmi_production.recycleview_4.db.Network.ApiService
import com.example.holmi_production.recycleview_4.db.Network.NewsObject
import com.example.holmi_production.recycleview_4.db.Network.SingleNews
import com.example.holmi_production.recycleview_4.db.dao.FavoriteDao
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class NewsRepository(context: Context) {


    private val db: NewsDatabase = NewsDatabase.getInstance(context)!!
    private val newsDao: NewsDao
    private val favoriteNewsDao: FavoriteNewsDao
    private val fNews: FavoriteDao
    private val apiClient: ApiService

    init {
        newsDao = db.newsDao()
        favoriteNewsDao = db.favoriteNewsDao()
        fNews = db.favorite()
        apiClient = ApiClient().getClient().create(ApiService::class.java)
    }

    fun getNewsFromNetwork(): Single<NewsObject> {
        return apiClient.getNews()
            .subscribeOn(Schedulers.io())
            .doAfterSuccess { t ->
                insertListNews(t.news)
            }
    }

    fun getNewsFromNetworkById(id: Int): Single<SingleNews> {
        return apiClient.getNewsById(id)
            .subscribeOn(Schedulers.io())
    }

    fun insertFavoriteNews(news: FavoriteNews): Completable {
        return Completable.fromCallable { favoriteNewsDao.insert(news) }
            .subscribeOn(Schedulers.io())
    }

    fun insertListNews(news: List<News>): Completable {
        return Completable.fromCallable { newsDao.insertListNews(news) }
            .subscribeOn(Schedulers.io())
    }

    fun deleteFavotiteNews(newsId: Int): Completable {
        return Completable.fromCallable { favoriteNewsDao.delete(newsId) }
            .subscribeOn(Schedulers.io())
    }

    fun getAllNews(): Flowable<List<News>> {
        return newsDao.getAll()
            .subscribeOn(Schedulers.io())
    }

    fun getAllFavoriteNews(): Flowable<List<News>> {
        return fNews.getFavorite()
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