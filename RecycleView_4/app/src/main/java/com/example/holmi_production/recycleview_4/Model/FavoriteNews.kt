package com.example.holmi_production.recycleview_4.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FavoriteNews(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo
    val newsId:Int
)