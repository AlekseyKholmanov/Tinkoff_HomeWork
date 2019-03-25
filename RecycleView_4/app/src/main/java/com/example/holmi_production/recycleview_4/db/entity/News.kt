package com.example.holmi_production.recycleview_4.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.example.holmi_production.recycleview_4.Converter.DateConverter
import java.util.*

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    var theme: String,

    var date: Date,

    var content: String,

    var isFavorites: Boolean
)