package com.example.holmi_production.recycleview_4.Model

import com.example.holmi_production.recycleview_4.db.entity.News
import com.google.gson.annotations.SerializedName

class NewsObject(
    @SerializedName("payload")
    val news: List<News>
)

class SingleNews(
    @SerializedName("payload")
    val newsItem: NewsItem
)

class NewsItem(
    @SerializedName("title")
    val newsHeader: News,
    @SerializedName("content")
    val content: String
)