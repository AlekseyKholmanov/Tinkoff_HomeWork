package com.example.holmi_production.recycleview_4.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TinkoffApiResonce<T>(
    @SerializedName("payload")
    val listNews:T
):Serializable

