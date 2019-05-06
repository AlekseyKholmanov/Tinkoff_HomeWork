package com.example.holmi_production.recycleview_4.mvp.Presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.view.NewsListView
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class NewsListPresenter @Inject constructor(private val newsRepository: NewsRepository) : MvpPresenter<NewsListView>(), INewsListPresenter {


    override fun openSingleNews(newsId:Int) {
        viewState.showSingleNews(newsId)
    }

    override fun getNews(isFavorite: Boolean) {
        if (!isFavorite)
        newsRepository.getNewsFromNetwork()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { newsObject ->
                DateUtils.reformateItem(newsObject.news)
            }
            .subscribe { listItem ->
                viewState.showNews(listItem)
            }
        else{
            newsRepository.getAllFavoriteNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    DateUtils.reformateItem(t)
                }
                .subscribe {
                    viewState.showNews(it)
                }
        }

    }

}