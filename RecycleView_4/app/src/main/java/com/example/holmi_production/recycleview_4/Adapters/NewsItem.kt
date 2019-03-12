package com.example.holmi_production.recycleview_4.Adapters

import com.example.holmi_production.recycleview_4.Model.News

class NewsItem(val content:News): ListItem() {
    override fun getType(): Int {
        return TYPE_NEWS
    }
}