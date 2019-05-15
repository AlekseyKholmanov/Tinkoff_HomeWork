package com.example.holmi_production.recycleview_4.model


import android.util.Log
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import com.example.holmi_production.recycleview_4.api.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    newsDatabase: NewsDatabase,
    private val remoteDataSource: RemoteDataSource
) {
    private val newsDao = newsDatabase.newsDao()
    private val favoriteNewsDao = newsDatabase.favoriteNewsDao()
    private val favorite = newsDatabase.favorite()
    private val viewedDao = newsDatabase.viewedNews()


    fun getNewsFromNetwork(): Single<TinkoffApiResonce<List<News>>> {
        return remoteDataSource.getNews()
            .doAfterSuccess { t ->
                insertListNews(t.listNews
                    .sortedByDescending { news -> news.date.timeInMilliseconds }
                    .take(100))
                    .subscribe {
                        Log.d("qqwerty", "inserted")
                    }
            }
    }

    fun getViewedNewsById(id: Int): Maybe<ViewedContent> {
        return viewedDao.getNewsWithContent(id)
    }


    fun getAllContentIds(): Single<List<Int>> {
        return viewedDao.getAllIdsWithContent()
    }

    fun insertViewedNews(viewedContent: ViewedContent): Completable {
        return Completable.fromCallable { viewedDao.insert(viewedContent) }
    }

    fun insertNews(news: News): Completable {
        return Completable.fromCallable { newsDao.insert(news) }
    }

    fun getNewsById(newsId: Int): Single<News> {
        return newsDao.getNewsById(newsId)
    }

    private fun deleteAllNews() {
        return newsDao.deleteAll()
    }

    fun getNewsFromNetworkById(id: Int): Single<TinkoffApiResonce<NewsItem>> {
        return remoteDataSource.getNewsById(id)
    }

    fun insertFavoriteNews(news: FavoriteNews): Completable {
        return Completable.fromCallable { favoriteNewsDao.insert(news) }
    }

    fun insertListNews(news: List<News>): Completable {
        return Completable.fromCallable { newsDao.insertListNews(news) }
    }

    fun deleteFavotiteNews(newsId: Int): Completable {
        return Completable.fromCallable { favoriteNewsDao.delete(newsId) }
    }

    fun getAllFavoriteNews(): Flowable<List<News>> {
        return favorite.getFavorite()
    }

    fun getFavoriteNewsById(newsId: Int): Maybe<FavoriteNews> {
        return favoriteNewsDao.getNewsById(newsId)
    }
}