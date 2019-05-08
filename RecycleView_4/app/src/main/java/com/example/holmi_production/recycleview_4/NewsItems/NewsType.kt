package com.example.holmi_production.recycleview_4.NewsItems

import com.example.holmi_production.recycleview_4.source.db.entity.News

class NewsType(val content: News) : NewsContainer() {
    override fun getType(): Int {
        return TYPE_NEWS
    }
}