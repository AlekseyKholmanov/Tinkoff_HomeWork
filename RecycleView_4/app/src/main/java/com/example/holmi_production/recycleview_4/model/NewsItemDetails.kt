package com.example.holmi_production.recycleview_4.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class NewsItemDetails(
    @Embedded
    @PrimaryKey
    @SerializedName("title")
    val title: NewsItemTitle,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String
) : Serializable, LisItem