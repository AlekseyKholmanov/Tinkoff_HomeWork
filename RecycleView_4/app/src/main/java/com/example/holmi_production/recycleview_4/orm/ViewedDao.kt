package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.model.ViewedContent
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ViewedNewsDao{

    @Query("SELECT * FROM ViewedContent WHERE id=:newsId")
    fun getNewsWithContent(newsId:Int):Maybe<ViewedContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(viewedContent: ViewedContent)

    @Query("SELECT id FROM ViewedContent")
    fun getAllIdsWithContent(): Single<List<Int>>
}