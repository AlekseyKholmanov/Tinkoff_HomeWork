package com.example.holmi_production.recycleview_4.Presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.MvpView.NewsListView
import com.example.holmi_production.recycleview_4.db.entity.News

@InjectViewState
class NewsListPresenterImpl : MvpPresenter<NewsListView>(), NewsListPresenter {
    override fun openSingleNews(newsId:Int) {
        viewState.showSingleNews(newsId)
    }

    override fun getNews(list: List<News>, isFavorite: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}