package com.example.holmi_production.recycleview_4.storage

import com.example.holmi_production.recycleview_4.api.NewsService
import com.example.holmi_production.recycleview_4.model.FavoriteNews
import com.example.holmi_production.recycleview_4.model.NewsItemDetails
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailsRepository @Inject constructor(
    database: NewsDatabase,
    private val newsService: NewsService
) {
    private val favoriteNewsDao by lazy { database.favoriteNewsDao() }
    private val viewedDao by lazy { database.viewedNews() }

    fun changeFavoriteState(newsId: String, isFavorite: Boolean): Completable {
        return Completable.fromCallable {
            if (isFavorite)
                favoriteNewsDao.insert(FavoriteNews(newsId))
            else
                favoriteNewsDao.delete(newsId)
        }
    }

    fun getViewedNewsById(id: String): Single<NewsItemDetails> {
        return getNewsFromNetworkById(id)
            .onErrorResumeNext { getDetailsFromDb(id).toSingle() }
    }

    fun isFavorite(newsId: String): Single<Boolean> {
        return favoriteNewsDao.contains(newsId)
    }

    private fun getNewsFromNetworkById(id: String): Single<NewsItemDetails> {
        return newsService.getNewsById(id)
            .map { it.listNews }
            .doOnSuccess {
                viewedDao.insert(it)
            }
    }


    private fun getDetailsFromDb(id: String): Maybe<NewsItemDetails> = viewedDao.getNewsWithContent(id)
}