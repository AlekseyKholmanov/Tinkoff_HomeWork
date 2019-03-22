package com.example.holmi_production.recycleview_4.Model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.support.annotation.Nullable

@Dao
public interface FavoriteNewsDao{

    @Nullable
    @Query("select * from favoritenews")
    fun getAllId():List<FavoriteNews>

    @Insert
    fun insert(favoriteNews: FavoriteNews)

    @Delete()
    fun deleteId(favoriteNews: FavoriteNews)
}