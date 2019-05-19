package com.example.holmi_production.recycleview_4.storage


import android.util.Log
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import com.example.holmi_production.recycleview_4.api.NewsService
import com.example.holmi_production.recycleview_4.model.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    newsDatabase: NewsDatabase,
    private val newsService: NewsService
) {
    private val newsDao = newsDatabase.newsDao()

    private val favorite = newsDatabase.favorite()
    private val viewedDao = newsDatabase.viewedNews()

    fun getNews(): Single<List<News>> {
        return getAllNewsFromDb()
            .switchIfEmpty {getNewsFromNetwork()
            }


    }

    private fun getNewsFromNetwork(): Single<List<News>> {
        return newsService.getNews()
            .map { it.listNews }
            .doOnSuccess {
                newsDao.insertListNews(it)
            }
    }
    private fun getAllNewsFromDb(): Maybe<List<News>> {
        return newsDao.getAllNews()
            .filter { it.isNotEmpty() }
    }



    fun insertNews(news: News): Completable {
        return Completable.fromCallable { newsDao.insert(news) }
    }

    fun insertListNews(news: List<News>): Completable {
        return Completable.fromCallable { newsDao.insertListNews(news) }
    }

    fun getAllFavoriteNews(): Flowable<List<News>> {
        return favorite.getFavorite()
    }


}