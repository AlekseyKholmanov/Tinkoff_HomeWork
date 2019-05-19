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
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

@InjectViewState
class NewsDetailPresenter @Inject constructor(
    private val newsRepository: NewsRepository,
    private val cm: ConnectivityManager
) :
    BasePresenter<NewsDetailView>() {

    private lateinit var newsItemId: String

    private fun isInternetConnected(): Boolean {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun changeFavoriteState(isFavorite: Boolean) {
        newsRepository.changeFavoriteState(newsItemId, isFavorite)
            .async()
            .subscribe {
                viewState.showFavoriteChangedToast(isFavorite)
            }
            .keep()
    }

    fun getSingleNews(newsId: String) {
        this.newsItemId = newsId.toString()
        Singles.zip(
            newsRepository.getViewedNewsById(newsId),
            newsRepository.isFavorite(newsId)
        )
            .async()
            .subscribe { t1, _ ->
                viewState.showDetails(t1.first, t1.second)
            }
            .keep()

    }
}