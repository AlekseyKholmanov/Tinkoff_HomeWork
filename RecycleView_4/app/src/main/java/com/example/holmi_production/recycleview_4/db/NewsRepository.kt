package com.example.holmi_production.recycleview_4.db


import com.example.holmi_production.recycleview_4.Model.NewsObject
import com.example.holmi_production.recycleview_4.Model.RemoteDataSource
import com.example.holmi_production.recycleview_4.Model.SingleNews
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val remoteDataSource:RemoteDataSource
) {
    private val newsDao=newsDatabase.newsDao()
    private val favoriteNewsDao = newsDatabase.favoriteNewsDao()
    private val favorite = newsDatabase.favorite()


    fun getNewsFromNetwork(): Single<NewsObject> {
        return remoteDataSource.getNews()
            .subscribeOn(Schedulers.io())
            .doAfterSuccess { t ->
                insertListNews(t.news)
                    .subscribe()
            }
    }

    fun getNewsFromNetworkById(id: Int): Single<SingleNews> {
        return remoteDataSource.getNewsById(id)
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
        return favorite.getFavorite()
            .subscribeOn(Schedulers.io())
    }


    fun getFavoriteNewsById(newsId: Int): Maybe<FavoriteNews> {
        return favoriteNewsDao.getNewsById(newsId)
            .subscribeOn(Schedulers.io())
    }


}