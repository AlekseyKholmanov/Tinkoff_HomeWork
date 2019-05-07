package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import java.util.ArrayList

@StateStrategyType(AddToEndSingleStrategy::class)
interface ListNewsView : MvpView {
    fun showNews(news: ArrayList<ListItem>)
    fun showFavoriteNews(news: ArrayList<ListItem>)
    fun showRefreshingStart()
    fun showRefreshingEnd()
    @StateStrategyType(SkipStrategy::class)
    fun showSingleNews(newsId: Int)
    fun updateListNews()
    fun showNetworkAlertDialog()
}