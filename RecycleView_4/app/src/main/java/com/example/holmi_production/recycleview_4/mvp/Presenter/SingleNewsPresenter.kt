package com.example.holmi_production.recycleview_4.mvp.Presenter

interface SingleNewsPresenter{
    fun addToFavorite(newsId:Int)
    fun deletefromFavorite(newsId: Int)
    fun getSingleNews(newsId: Int)
    fun isFavoteNews(newsId: Int)
}