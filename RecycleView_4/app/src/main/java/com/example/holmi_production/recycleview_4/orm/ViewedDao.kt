package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.model.NewsItemDetails
import io.reactivex.Maybe

@Dao
interface ViewedNewsDao{

    @Query("SELECT * FROM NewsItemDetails WHERE newsId=:newsId")
    fun getNewsWithContent(newsId:String):Maybe<NewsItemDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(viewedContent: NewsItemDetails)
}