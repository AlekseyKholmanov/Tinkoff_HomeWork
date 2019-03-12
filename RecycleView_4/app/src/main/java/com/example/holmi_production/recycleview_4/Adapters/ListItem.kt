package com.example.holmi_production.recycleview_4.Adapters

public abstract class ListItem{

    val TYPE_HEADER = 0
    val TYPE_NEWS = 1

    abstract fun  getType():Int

}