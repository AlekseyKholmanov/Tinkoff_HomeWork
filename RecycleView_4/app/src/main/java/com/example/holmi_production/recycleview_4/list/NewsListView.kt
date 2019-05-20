package com.example.holmi_production.recycleview_4.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.model.News
import java.util.ArrayList

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsListView : MvpView {
    fun showNews(news: ArrayList<NewsContainer>)
    fun showError(error:Throwable)

    fun showLoading(show:Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showSingleNews(news: News)

    fun onInternetStateChanged(connected: Boolean)
}