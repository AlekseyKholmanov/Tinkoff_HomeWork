package com.example.holmi_production.recycleview_4.list

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.recycleview_4.async
import com.example.holmi_production.recycleview_4.model.News
import com.example.holmi_production.recycleview_4.mvp.BasePresenter
import com.example.holmi_production.recycleview_4.network.NetworkStateListener
import com.example.holmi_production.recycleview_4.storage.NewsListRepository
import com.example.holmi_production.recycleview_4.utils.DateUtils
import javax.inject.Inject

@InjectViewState
class NewsListPresenter @Inject constructor(
    private val repository: NewsListRepository,
    private val networkStateListener: NetworkStateListener
) :
    BasePresenter<NewsListView>() {

    private var internetState: Boolean = true

    fun getFavoriteNews() {
        viewState.showLoading(true)
        repository.getAllFavoriteNews()
            .async()
            .map { t ->
                DateUtils.reformateItem(t)
            }.subscribe ({ listItem ->
                viewState.showLoading(false)
                viewState.showNews(listItem)
            },{t->
                viewState.showLoading(false)
                viewState.showError(t)
            }).keep()
    }

    fun subscribeToNetworkChanges() {
        networkStateListener.ObserveNewtworkState()
            .filter { it != internetState }
            .doOnNext {
                internetState = it
            }
            .subscribe { connected ->
                internetState = connected
                viewState.onInternetStateChanged(connected)
            }
            .keep()
    }

    fun openSingleNews(news: News) {
        viewState.showSingleNews(news)
    }

    fun getNews(force:Boolean) {
        viewState.showLoading(true)
        repository.getNews(force)
            .async()
            .doAfterTerminate {
                viewState.showLoading(false)
            }
            .map { it-> DateUtils.reformateItem(it) }
            .subscribe { listItem ->
                viewState.showNews(listItem)
            }
            .keep()
    }
}

