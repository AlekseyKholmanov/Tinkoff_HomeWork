package com.example.holmi_production.recycleview_4.model


import android.util.Log
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import com.example.holmi_production.recycleview_4.api.NewsService
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
    private val favoriteNewsDao = newsDatabase.favoriteNewsDao()
    private val favorite = newsDatabase.favorite()
    private val viewedDao = newsDatabase.viewedNews()


    fun getNewsFromNetwork(): Single<TinkoffApiResonce<List<News>>> {
        return newsService.getNews()
            .doAfterSuccess { t ->
                insertListNews(t.listNews
                    .sortedByDescending { news -> news.date.timeInMilliseconds }
                    .take(100))
                    .subscribe {
                        Log.d("qqwerty", "inserted")
                    }
            }
    }

    fun getViewedNewsById(id: String): Single<ViewedContent> {
        return getDetailsFromDb(id)
            .switchIfEmpty(getNewsFromNetworkById(id))
            .onErrorResumeNext(getDetailsFromDb(id).toSingle())

    }

    private fun getDetailsFromDb(id:String):Maybe<ViewedContent> = viewedDao.getNewsWithContent(id)

    fun changeFavoriteState(newsId:String, isFavorite:Boolean): Completable {
        return Completable.fromCallable {
            if(isFavorite)
                favoriteNewsDao.insert(FavoriteNews(newsId))
            else
                favoriteNewsDao.delete(newsId)
        }
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

    fun getNewsFromNetworkById(id: String): Single<TinkoffApiResonce<NewsItem>> {
        return newsService.getNewsById(id)
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

    fun isFavorite(newsId: Int): Single<Boolean> {
        return favoriteNewsDao.contains(newsId)
    }
}