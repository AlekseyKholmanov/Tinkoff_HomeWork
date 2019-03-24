package com.example.holmi_production.recycleview_4.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    var theme: String,
    @ColumnInfo
    var date: Date,
    @ColumnInfo
    var content: String,
    @ColumnInfo
    var isFavorites: Boolean
)