package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import java.util.ArrayList

@StateStrategyType(AddToEndSingleStrategy::class)
interface ListNewsView : MvpView {
    fun showNews(news: ArrayList<NewsContainer>)

    fun showFavoriteNews(news: ArrayList<NewsContainer>)

    @StateStrategyType(SkipStrategy::class)
    fun showRefreshingStart()

    @StateStrategyType(SkipStrategy::class)
    fun showRefreshingEnd()

    @StateStrategyType(SkipStrategy::class)
    fun showSingleNews(newsId: Int)

    fun updateListNews()

    @StateStrategyType(SkipStrategy::class)
    fun showNetworkAlertDialog()
}