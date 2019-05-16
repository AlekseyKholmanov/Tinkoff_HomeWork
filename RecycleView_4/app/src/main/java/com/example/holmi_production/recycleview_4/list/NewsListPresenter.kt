package com.example.holmi_production.recycleview_4.list

import android.net.ConnectivityManager
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.async
import com.example.holmi_production.recycleview_4.model.News
import com.example.holmi_production.recycleview_4.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.BasePresenter
import com.example.holmi_production.recycleview_4.network.NetworkStateListener
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

@InjectViewState
class NewsListPresenter @Inject constructor(
    private val newsRepository: NewsRepository,
    private val networkStateListener: NetworkStateListener
) :
    BasePresenter<NewsListView>() {

    private var isInternetConnected: Boolean = false

    fun getFavoriteNews() {
        newsRepository.getAllFavoriteNews()
            .async()
            .map { t ->
                DateUtils.reformateItem(t)
            }.subscribe { listItem ->
                viewState.dismissProgressBar()
                viewState.showNews(listItem)
            }.keep()
    }

    fun subscribeToNetworkChanges() {
        networkStateListener.ObserveNewtworkState()
            .subscribe { connected ->
                isInternetConnected = connected
                viewState.onInternetStateChanged(connected)
            }
            .keep()
    }

    fun updateNews(isFavorite: Boolean) {
        if (isInternetConnected) {
            viewState.showRefreshingStart()
            callNews()
                .subscribe { listItem ->
                    viewState.showRefreshingEnd()
                    viewState.showNews(listItem)
                }
                .keep()

        } else {
            viewState.showRefreshingEnd()
            viewState.showNetworkAlertDialog()
        }
    }

    fun openSingleNews(news: News) {
            viewState.showSingleNews(news)
    }

    fun getNews() {
        if (isInternetConnected) {
            callNews()
                .subscribe { listItem ->
                    viewState.dismissProgressBar()
                    viewState.showNews(listItem)
                }
                .keep()

        } else {
            viewState.dismissProgressBar()
            viewState.showNetworkAlertDialog()
        }
    }

    private fun callNews(): Single<ArrayList<NewsContainer>> {
        return newsRepository.getNewsFromNetwork()
            .async()
            .map { newsObject ->
                DateUtils.reformateItem(newsObject.listNews)
            }

    }
}