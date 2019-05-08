package com.example.holmi_production.recycleview_4.mvp.Presenter

import android.net.ConnectivityManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.view.ListNewsView
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

@InjectViewState
class NewsFragmentPresenterImp @Inject constructor(
    private val newsRepository: NewsRepository,
    private val cm: ConnectivityManager
) :
    MvpPresenter<ListNewsView>(), NewsFragmentsPresenter {

    private fun isInternetConnected(): Boolean {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    override fun updateNews(isFavorite: Boolean) {
        if (isInternetConnected()) {
            if (!isFavorite) {
                viewState.showRefreshingStart()
                compositeDisposable.add(callNews()
                    .subscribe { listItem ->
                        viewState.showRefreshingEnd()
                        viewState.showNews(listItem)
                    })
            } else {
                viewState.showRefreshingStart()
                compositeDisposable.add(
                    callFavoriteNews().subscribe { listItem ->
                        viewState.showRefreshingEnd()
                        viewState.showNews(listItem)
                    })
            }

        } else {
            viewState.showRefreshingEnd()
            viewState.showNetworkAlertDialog()
        }
    }

    private val compositeDisposable = CompositeDisposable()

    override fun openSingleNews(newsId: Int) {
        viewState.showSingleNews(newsId)
    }

    override fun getNews(isFavorite: Boolean) {
        if (isInternetConnected()) {
            if (!isFavorite) {
                compositeDisposable.add(callNews()
                    .subscribe { listItem ->
                        viewState.showNews(listItem)
                    })
            } else {
                compositeDisposable.add(callFavoriteNews()
                    .subscribe { it ->
                        viewState.showFavoriteNews(it)
                    })
            }
        } else
            viewState.showNetworkAlertDialog()
    }

    private fun callNews(): Single<ArrayList<NewsContainer>> {
        return newsRepository.getNewsFromNetwork()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { newsObject ->
                DateUtils.reformateItem(newsObject.listNews)
            }
    }

    private fun callFavoriteNews(): Flowable<ArrayList<NewsContainer>> {
        return newsRepository.getAllFavoriteNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t ->
                DateUtils.reformateItem(t)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}