package com.example.holmi_production.recycleview_4.NewsItems

import com.example.holmi_production.recycleview_4.model.NewsItemTitle

class NewsType(val content: NewsItemTitle) : NewsContainer() {
    override fun getType(): Int {
        return TYPE_NEWS
    }
}