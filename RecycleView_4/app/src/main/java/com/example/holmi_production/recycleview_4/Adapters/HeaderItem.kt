package com.example.holmi_production.recycleview_4.Adapters

import java.util.*

public class HeaderItem(val date: Date) : ListItem() {

    override fun getType(): Int {
        return  TYPE_HEADER
    }
}