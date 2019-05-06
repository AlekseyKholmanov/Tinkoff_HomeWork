package com.example.holmi_production.recycleview_4.mvp.Presenter

import com.example.holmi_production.recycleview_4.source.db.entity.News
import javax.inject.Inject

interface INewsListPresenter {
    fun getNews(isFavorite:Boolean)
    fun openSingleNews(newsId:Int)
}