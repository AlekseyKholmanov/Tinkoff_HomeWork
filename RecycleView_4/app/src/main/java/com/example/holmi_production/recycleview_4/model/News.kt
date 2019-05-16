package com.example.holmi_production.recycleview_4.model

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class News(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var newsId: Int,

    @SerializedName("text")
    var theme: String,

    @SerializedName("publicationDate")
    @Embedded
    var date: PublicationDate
)