package com.example.holmi_production.recycleview_4.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FavoriteNews(
    @PrimaryKey(autoGenerate = false)
    val newsId: String
)