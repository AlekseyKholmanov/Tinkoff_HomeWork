package com.example.holmi_production.recycleview_4.source.network

import com.example.holmi_production.recycleview_4.source.db.entity.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource{
    @GET("news")
    fun getNews():Single<TinkoffApiResonce<List<News>>>

    @GET("news_content?")
    fun getNewsById(@Query("id") newsId:Int):Single<TinkoffApiResonce<NewsItem>>
}