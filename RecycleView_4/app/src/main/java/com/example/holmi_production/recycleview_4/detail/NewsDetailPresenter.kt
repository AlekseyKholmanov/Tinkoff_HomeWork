package com.example.holmi_production.recycleview_4.detail

import android.net.ConnectivityManager
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.recycleview_4.async
import com.example.holmi_production.recycleview_4.model.NewsRepository
import com.example.holmi_production.recycleview_4.model.FavoriteNews
import com.example.holmi_production.recycleview_4.model.ViewedContent
import com.example.holmi_production.recycleview_4.model.NewsItem
import com.example.holmi_production.recycleview_4.mvp.BasePresenter
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

@InjectViewState
class NewsDetailPresenter @Inject constructor(
    private val newsRepository: NewsRepository,
    private val cm: ConnectivityManager
) :
    BasePresenter<NewsDetailView>() {


    private fun isInternetConnected(): Boolean {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }


    fun checkFavorite(newsId: Int) {
        newsRepository.getFavoriteNewsById(newsId)
            .async()
            .doOnError {
                Log.d("qwery", it.toString())
            }
            .subscribe { favorite ->
                viewState.setFavorite(favorite != null)
            }
            .keep()

    }

    fun deletefromFavorite(newsId: Int) {newsRepository.deleteFavotiteNews(newsId)
            .async()
            .subscribe {
                viewState.showUnfavoriteIcon()
                viewState.showToast()
            }
        .keep()
    }

    fun addToFavorite(newsId: Int) {
            newsRepository.insertFavoriteNews(
                FavoriteNews(
                    null,
                    newsId
                )
            )
                .async()
                .subscribe {
                    viewState.showFavoriteIcon()
                    viewState.showToast()
                }
                .keep()
    }

    fun getSingleNews(newsId: Int) {
        if (isInternetConnected()) {
                newsRepository.getNewsFromNetworkById(newsId)
                    .async()
                    .map { it ->
                        NewsItem(
                            it.listNews.newsHeader,
                            it.listNews.content
                        )
                    }
                    .doAfterSuccess { it ->
                        //сохранение полученной новости в локальный репозиторий
                        val viewedNews = ViewedContent(
                            it.newsHeader.newsId,
                            it.content
                        )
                        newsRepository.insertViewedNews(viewedNews)
                            .async()
                            .doOnError { Log.d("qwerty", it.toString()) }
                            .subscribe {
                                Log.d("qwerty", "inserted" + it.newsHeader.newsId)
                            }
                    }
                    .subscribe { listItem ->
                        viewState.showNews(listItem)
                    }
                    .keep()
        } else {
                Singles.zip(newsRepository.getViewedNewsById(newsId).toSingle(), newsRepository.getNewsById(newsId))
                    .map { it ->
                        NewsItem(
                            newsHeader = it.second,
                            content = it.first.viewedContent
                        )
                    }
                    .async()
                    .doOnError {
                        Log.d("qwerty", it.toString())
                    }
                    .subscribe { news ->
                        viewState.showNews(news)
                    }
                    .keep()
        }
    }
}