package com.example.holmi_production.recycleview_4.source.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ViewedNews(
    @PrimaryKey(autoGenerate = false)
    var id: Int?,

    var content: String
)
