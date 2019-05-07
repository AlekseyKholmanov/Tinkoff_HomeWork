package com.example.holmi_production.recycleview_4.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.recycleview_4.source.network.NewsItem

interface SingleNewsView: MvpView{
    fun showNews(listItem: NewsItem)
    fun showToast()
}