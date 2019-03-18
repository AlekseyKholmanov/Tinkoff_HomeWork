package com.example.holmi_production.recycleview_4.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo
    val theme: String,
    @ColumnInfo
    val date: Date,
    @ColumnInfo
    val content: String,
    @ColumnInfo
    val isFavorites: Boolean
)