package com.example.holmi_production.recycleview_4.mvp.Presenter

interface INewsListPresenter {
    fun getNews(isFavorite:Boolean)
    fun getFavoriteNews()
    fun openSingleNews(newsId:Int)
    fun updateNews(isFavorite:Boolean)
}