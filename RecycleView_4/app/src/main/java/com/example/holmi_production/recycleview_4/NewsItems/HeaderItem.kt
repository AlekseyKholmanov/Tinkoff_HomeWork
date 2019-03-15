package com.example.holmi_production.recycleview_4.NewsItems

import java.util.*

public class HeaderItem(val date: Date) : ListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}