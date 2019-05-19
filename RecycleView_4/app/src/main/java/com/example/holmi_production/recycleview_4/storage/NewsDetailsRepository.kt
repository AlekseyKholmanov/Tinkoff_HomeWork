package com.example.holmi_production.recycleview_4.storage

import com.example.holmi_production.recycleview_4.api.NewsService
import com.example.holmi_production.recycleview_4.model.FavoriteNews
import com.example.holmi_production.recycleview_4.model.NewsItem
import com.example.holmi_production.recycleview_4.model.TinkoffApiResonce
import com.example.holmi_production.recycleview_4.model.ViewedContent
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

    fun getViewedNewsById(id: String): Single<ViewedContent> {
        return getNewsFromNetworkById(id)
            .map {
                ViewedContent(
                    id = null,
                    viewedContent = it.listNews.content
                )
            }
            .onErrorResumeNext { getDetailsFromDb(id).toSingle() }
    }

    fun isFavorite(newsId: String): Single<Boolean> {
        return favoriteNewsDao.contains(newsId)
    }

    fun insertViewedNews(viewedContent: ViewedContent): Completable {
        return Completable.fromCallable { viewedDao.insert(viewedContent) }
    }

    private fun getNewsFromNetworkById(id: String): Single<TinkoffApiResonce<NewsItem>> {
        return newsService.getNewsById(id)
    }


    private fun getDetailsFromDb(id: String): Maybe<ViewedContent> = viewedDao.getNewsWithContent(id)
}