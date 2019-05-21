package com.example.holmi_production.recycleview_4.api

import com.example.holmi_production.recycleview_4.model.NewsItemDetails
import com.example.holmi_production.recycleview_4.model.TinkoffApiResonce
import com.example.holmi_production.recycleview_4.model.NewsItemTitle
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService{
    @GET("news")
    fun getNews():Single<TinkoffApiResonce<List<NewsItemTitle>>>

    @GET("news_content?")
    fun getNewsById(@Query("id") newsId:String):Single<TinkoffApiResonce<NewsItemDetails>>
}