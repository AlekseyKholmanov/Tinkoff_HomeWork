package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import java.util.ArrayList


interface ListNewsView: MvpView{
    fun showNews(news: ArrayList<ListItem>)

    fun showSingleNews(newsId:Int)
    fun updateListNews()
    fun showNetworkAlertDialog()
}