package com.example.holmi_production.recycleview_4.source.db.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.Nullable
import com.example.holmi_production.recycleview_4.source.db.entity.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface NewsDao{
    @Query("SELECT * FROM News WHERE newsId=:idToSelect")
    fun getNewsById(idToSelect: Int): Single<News>

    @Query("Select * from News where newsId in (:newsIds)")
    fun getNewsByIds(newsIds:List<Int>):Flowable<List<News>>

    @Query("SELECT * FROM News")
    fun  getAll():Flowable<List<News>>

    @Insert(onConflict = REPLACE)
    fun insert(news: News)

    @Insert(onConflict = REPLACE)
    fun insertListNews(news:List<News>)

    @Delete
    fun delete(news: News)

    @Update
    fun update(news: News)

    @Query("DELETE FROM news")
    fun deleteAll()
}
