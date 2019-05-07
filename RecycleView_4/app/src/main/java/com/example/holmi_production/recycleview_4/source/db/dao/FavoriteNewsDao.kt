package com.example.holmi_production.recycleview_4.source.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.source.db.entity.FavoriteNews
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface FavoriteNewsDao{

    @Nullable
    @Query("select newsId from FavoriteNews")
    fun getAllFavoriteIds(): Flowable<List<Int>>

    @Nullable
    @Query("select * from FavoriteNews where newsId Like :newsId")
    fun getNewsById(newsId: Int):Maybe<FavoriteNews>

    @Insert(onConflict = REPLACE)
    fun insert(favoriteNews: FavoriteNews)

    @Query("Delete from FavoriteNews where newsId=:newsId ")
    fun delete(newsId: Int)

    @Query("DELETE FROM FavoriteNews")
    fun deleteAll()
}