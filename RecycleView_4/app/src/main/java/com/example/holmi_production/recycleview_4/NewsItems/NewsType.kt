package com.example.holmi_production.recycleview_4.NewsItems

import com.example.holmi_production.recycleview_4.model.News

class NewsType(val content: News) : NewsContainer() {
    override fun getType(): Int {
        return TYPE_NEWS
    }
}