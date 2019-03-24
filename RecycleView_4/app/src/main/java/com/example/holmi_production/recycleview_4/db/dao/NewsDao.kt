package com.example.holmi_production.recycleview_4.db.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.db.entity.News


@Dao
public interface NewsDao{
    @Nullable
    @Query("SELECT * FROM news WHERE id=:idToSelect")
    fun getNewsById(idToSelect:Int): News

    @Query("SELECT * FROM news")
    fun  getAll():List<News>

    @Insert(onConflict = REPLACE)
    fun insert(news: News)

    @Insert
    fun insertData(news:List<News>)

    @Delete
    fun delete(news: News)

    @Update
    fun update(news: News)
}
