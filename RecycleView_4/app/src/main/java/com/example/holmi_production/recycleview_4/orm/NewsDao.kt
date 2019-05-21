package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.holmi_production.recycleview_4.model.NewsItemTitle
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface NewsDao{
    @Query("SELECT * FROM NewsItemTitle WHERE newsId=:idToSelect")
    fun getNewsById(idToSelect: Int): Single<NewsItemTitle>

    @Query("Select * from NewsItemTitle where newsId in (:newsIds)")
    fun getNewsByIds(newsIds:List<Int>):Flowable<List<NewsItemTitle>>

    @Query("SELECT * FROM NewsItemTitle")
    fun  getAllNews():Single<List<NewsItemTitle>>

    @Insert(onConflict = REPLACE)
    fun insert(news: NewsItemTitle)

    @Insert(onConflict = REPLACE)
    fun insertListNews(news:List<NewsItemTitle>)

    @Delete
    fun delete(news: NewsItemTitle)

    @Query("DELETE FROM NewsItemTitle")
    fun deleteAll()
}
