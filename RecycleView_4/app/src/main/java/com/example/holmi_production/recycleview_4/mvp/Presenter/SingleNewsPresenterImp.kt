package com.example.holmi_production.recycleview_4.mvp.Presenter

import android.net.ConnectivityManager
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.view.SingleNewsView
import com.example.holmi_production.recycleview_4.source.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.source.db.entity.News
import com.example.holmi_production.recycleview_4.source.network.NewsItem
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@InjectViewState
class SingleNewsPresenterImp @Inject constructor(
    private val newsRepository: NewsRepository,
    private val cm: ConnectivityManager
) :
    MvpPresenter<SingleNewsView>(), SingleNewsPresenter {

    private val compositeDisposable = CompositeDisposable()

    private fun isInternetConnected(): Boolean {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }


    override fun checkFavorite(newsId: Int) {
        compositeDisposable.add(
            newsRepository.getFavoriteNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.d("qwery", it.toString())
                }
                .subscribe { favorite ->
                    viewState.setFavorite(favorite != null)
                })

    }

    override fun deletefromFavorite(newsId: Int) {
        compositeDisposable.add(newsRepository.deleteFavotiteNews(newsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.showUnfavoriteIcon()
                viewState.showToast()
            })
    }

    override fun addToFavorite(newsId: Int) {
        compositeDisposable.add(
            newsRepository.insertFavoriteNews(FavoriteNews(null, newsId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.showFavoriteIcon()
                    viewState.showToast()
                })
    }

    override fun getSingleNews(newsId: Int) {
        if (isInternetConnected()) {
            compositeDisposable.add(
                newsRepository.getNewsFromNetworkById(newsId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterSuccess { it ->
                        //сохранение полученной новости в локальный репозиторий
                        val news = News(
                            it.listNews.newsHeader.newsId,
                            it.listNews.newsHeader.theme,
                            it.listNews.newsHeader.date,
                            it.listNews.content
                        )
                        newsRepository.insertNews(news)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError { Log.d("qwerty", it.toString()) }
                            .subscribe()
                    }
                    .subscribe { listItem ->
                        viewState.showNews(listItem.listNews)
                    })
        } else {
            compositeDisposable.add(
                newsRepository.getNewsById(newsId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { news ->
                        val content = if(news.content == null)
                            ""
                        else
                            news.content!!
                        NewsItem(newsHeader = news, content = content)
                    }

                    .doOnError { Log.d("qwerty", it.toString()) }
                    .subscribe { news ->
                        viewState.showNews(news)
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}