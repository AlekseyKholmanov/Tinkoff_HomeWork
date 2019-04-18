package com.example.holmi_production.recycleview_4.db.Network

import com.example.holmi_production.recycleview_4.db.entity.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{
    @GET("news")
    fun getNews():Call<List<News>>

    @GET("news_content?")
    fun getNewsById(@Query("id") newsId:Int):Call<News>
}