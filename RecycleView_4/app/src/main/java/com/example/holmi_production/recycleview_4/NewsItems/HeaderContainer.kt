package com.example.holmi_production.recycleview_4.NewsItems

class HeaderContainer(val date: Long) : NewsContainer() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}