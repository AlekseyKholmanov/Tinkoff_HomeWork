package com.example.holmi_production.recycleview_4.source.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.source.db.entity.ViewedContent
import io.reactivex.Single

@Dao
interface ViewedNewsDao{

    @Query("SELECT * FROM ViewedContent WHERE id=:newsId")
    fun getNewsWithContent(newsId:Int):Single<ViewedContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(viewedContent: ViewedContent)

}