package com.example.holmi_production.recycleview_4.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.model.News
import java.util.ArrayList

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsListView : MvpView {
    fun showNews(news: ArrayList<NewsContainer>)

    fun showFavoriteNews(news: ArrayList<NewsContainer>)

    @StateStrategyType(SkipStrategy::class)
    fun showRefreshingStart()

    @StateStrategyType(SkipStrategy::class)
    fun showRefreshingEnd()

    @StateStrategyType(SkipStrategy::class)
    fun showSingleNews(news: News)

    fun updateListNews()

    @StateStrategyType(SkipStrategy::class)
    fun showNetworkAlertDialog()

    fun showProgessBar()
    fun dismissProgressBar()

    fun onInternetStateChanged(connected: Boolean)
}