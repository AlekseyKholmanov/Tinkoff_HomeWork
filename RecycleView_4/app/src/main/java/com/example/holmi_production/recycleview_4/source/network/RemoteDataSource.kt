package com.example.holmi_production.recycleview_4.source.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource{
    @GET("news")
    fun getNews():Single<NewsObject>

    @GET("news_content?")
    fun getNewsById(@Query("id") newsId:Int):Single<SingleNews>
}