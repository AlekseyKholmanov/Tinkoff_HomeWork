package com.example.holmi_production.recycleview_4.NewsItems

import java.util.*

class HeaderItem(val date: Long) : ListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}