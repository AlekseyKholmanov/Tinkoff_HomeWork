package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.recycleview_4.source.db.entity.News

interface NewsListView: MvpView{
    fun showNews(news:List<News>)
    fun showSingleNews(newsId:Int)
    fun updateListNews()
    fun showNetworkAlertDialog()
}