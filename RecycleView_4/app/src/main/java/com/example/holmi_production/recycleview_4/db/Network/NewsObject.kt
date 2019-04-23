package com.example.holmi_production.recycleview_4.db.Network

import com.example.holmi_production.recycleview_4.db.entity.News
import com.google.gson.annotations.SerializedName

class NewsObject(
    @SerializedName("payload")
    val news:List<News>
)