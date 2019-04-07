package com.example.holmi_production.recycleview_4.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import io.reactivex.Single

@Dao
public interface FavoriteNewsDao{

    @Nullable
    @Query("select newsId from favoritenews")
    fun getAllFavoriteIds(): Single<Array<Int>>

    @Nullable
    @Query("select * from favoritenews where newsId=:newsId")
    fun getNewsById(newsId: Int):FavoriteNews?

    @Insert(onConflict = REPLACE)
    fun insert(favoriteNews: FavoriteNews)

    @Query("Delete From favoritenews WHERE newsId=:newsId")
    fun delete(newsId: Int)

    @Query("DELETE FROM favoritenews")
    fun deleteAll()
}