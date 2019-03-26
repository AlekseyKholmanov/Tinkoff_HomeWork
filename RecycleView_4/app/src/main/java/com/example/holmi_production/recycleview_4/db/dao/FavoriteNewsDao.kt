package com.example.holmi_production.recycleview_4.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews

@Dao
public interface FavoriteNewsDao{

    @Nullable
    @Query("select * from favoritenews")
    fun getAll():LiveData<List<FavoriteNews>>

    @Insert
    fun insert(favoriteNews: FavoriteNews)

    @Delete
    fun delete(favoriteNews: FavoriteNews)
}