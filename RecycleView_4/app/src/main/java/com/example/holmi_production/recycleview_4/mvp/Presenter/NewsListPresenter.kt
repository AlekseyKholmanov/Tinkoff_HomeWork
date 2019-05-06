package com.example.holmi_production.recycleview_4.mvp.Presenter

import com.example.holmi_production.recycleview_4.source.db.entity.News

interface NewsListPresenter{
    fun getNews(list: List<News>, isFavorite:Boolean)
    fun openSingleNews(newsId:Int)
}