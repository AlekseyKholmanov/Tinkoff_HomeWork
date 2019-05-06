package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import java.util.ArrayList

interface NewsListView: MvpView{
    fun showNews(news: ArrayList<ListItem>)
    fun showSingleNews(newsId:Int)
    fun updateListNews()
    fun showNetworkAlertDialog()
}