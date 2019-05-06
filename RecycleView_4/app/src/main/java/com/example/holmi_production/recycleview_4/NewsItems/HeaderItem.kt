package com.example.holmi_production.recycleview_4.NewsItems

class HeaderItem(val date: Long) : ListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}