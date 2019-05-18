package com.example.holmi_production.recycleview_4.model

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class News(

    @SerializedName("id")
    @PrimaryKey
    var newsId: String,

    @SerializedName("text")
    var theme: String,

    @SerializedName("publicationDate")
    @Embedded
    var date: PublicationDate
)