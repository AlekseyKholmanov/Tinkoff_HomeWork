package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.model.FavoriteNews
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface FavoriteNewsDao{
    @Nullable
    @Query("select count(*) from FavoriteNews where newsId = :newsId")
    fun contains(newsId: String):Single<Boolean>

    @Insert(onConflict = REPLACE)
    fun insert(favoriteNews: FavoriteNews)

    @Query("Delete from FavoriteNews where newsId=:newsId ")
    fun delete(newsId: String)

    @Query("DELETE FROM FavoriteNews")
    fun deleteAll()
}