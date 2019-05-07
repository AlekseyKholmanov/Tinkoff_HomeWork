package com.example.holmi_production.recycleview_4.mvp.Presenter

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.view.ListNewsView
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ListNewsPresenter @Inject constructor(private val newsRepository: NewsRepository) :
    MvpPresenter<ListNewsView>(), INewsListPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun openSingleNews(newsId: Int) {
        viewState.showSingleNews(newsId)
    }

    override fun getNews() {
        compositeDisposable.add(
            newsRepository.getNewsFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { newsObject ->
                    DateUtils.reformateItem(newsObject.news)
                }
                .subscribe { listItem ->
                    viewState.showNews(listItem)
                })
    }

    override fun getFavoriteNews() {
        compositeDisposable.add(
            newsRepository.getAllFavoriteNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    DateUtils.reformateItem(t)
                }
                .subscribe {it->
                    viewState.showFavoriteNews(it)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}