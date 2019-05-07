package com.example.holmi_production.recycleview_4.mvp.Presenter

interface INewsListPresenter {
    fun getNews()
    fun getFavoriteNews()
    fun openSingleNews(newsId:Int)
}