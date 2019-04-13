package com.example.holmi_production.recycleview_4.NewsItems

abstract class ListItem {
    companion object {
        val TYPE_HEADER = 0
        val TYPE_NEWS = 1
    }

    abstract fun getType(): Int
}