package com.example.holmi_production.recycleview_4.NewsItems

import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.NewsItems.ListItem

class NewsItem(val content:News): ListItem() {
    override fun getType(): Int {
        return TYPE_NEWS
    }
}