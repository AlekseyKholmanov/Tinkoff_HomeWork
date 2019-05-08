package com.example.holmi_production.recycleview_4.mvp.Presenter

interface NewsFragmentsPresenter {
    fun getNews(isFavorite:Boolean)
    fun openSingleNews(newsId:Int)
    fun updateNews(isFavorite: Boolean)
}