package com.example.holmi_production.recycleview_4.Model

import java.util.*

data class News(
    var theme: String,
    var date: Date,
    var content: String,
    var isFavorites: Boolean
)