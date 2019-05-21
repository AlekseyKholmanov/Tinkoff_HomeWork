package com.example.holmi_production.recycleview_4.storage

import com.example.holmi_production.recycleview_4.api.NewsService
import com.example.holmi_production.recycleview_4.model.NewsItemTitle
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    database: NewsDatabase,
    private val newsService: NewsService
) {
    private val newsDao by lazy { database.newsDao() }
    private val favorite by lazy { database.favorite() }

    fun getNews(force: Boolean): Single<List<NewsItemTitle>> {
        return if (force)
            getNewsFromNetwork()
        else {
            getAllNewsFromDb()
                .filter { it -> it.isNotEmpty() }
                .switchIfEmpty(getNewsFromNetwork())
                .onErrorResumeNext { getAllNewsFromDb() }
        }
    }

    private fun getNewsFromNetwork(): Single<List<NewsItemTitle>> {
        return newsService.getNews()
            .map { it.listNews }
            .doOnSuccess {
                newsDao.insertListNews(it)
            }
    }

    private fun getAllNewsFromDb(): Single<List<NewsItemTitle>> {
        return newsDao.getAllNews()
    }

    fun getAllFavoriteNews(): Flowable<List<NewsItemTitle>> {
        return favorite.getFavorite()
    }
}