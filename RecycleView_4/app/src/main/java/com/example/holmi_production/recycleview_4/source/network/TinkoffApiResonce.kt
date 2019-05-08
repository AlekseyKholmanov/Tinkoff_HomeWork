package com.example.holmi_production.recycleview_4.source.network

import com.example.holmi_production.recycleview_4.source.db.entity.News
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TinkoffApiResonce<T>(
    @SerializedName("payload")
    val listNews:T
):Serializable

class NewsItem(
    @SerializedName("title")
    val newsHeader:News,
    @SerializedName("content")
    val content:String
)