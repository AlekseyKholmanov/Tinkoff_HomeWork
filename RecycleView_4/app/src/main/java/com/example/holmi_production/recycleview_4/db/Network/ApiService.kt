package com.example.holmi_production.recycleview_4.db.Network

import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("news")
    fun getNews():Single<NewsObject>

    @GET("news_content?")
    fun getNewsById(@Query("id") newsId:Int):Single<SingleNews>
}