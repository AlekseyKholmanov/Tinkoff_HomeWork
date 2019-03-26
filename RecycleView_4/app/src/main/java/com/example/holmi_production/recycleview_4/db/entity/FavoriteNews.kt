package com.example.holmi_production.recycleview_4.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FavoriteNews(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo
    var newsId:Int
)