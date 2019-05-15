package com.example.holmi_production.recycleview_4.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ViewedContent(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,

    val viewedContent: String
)
