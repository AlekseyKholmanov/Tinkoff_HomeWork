package com.example.holmi_production.recycleview_4.db.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class News(
    @Transient
    var traid: Int,

    @SerializedName("id")
    var newsId: Int,

    @SerializedName("text")
    var theme: String?,

    @SerializedName("publicationDate")
    @Embedded
    var date: PublicationDate,

    var content: String?
)