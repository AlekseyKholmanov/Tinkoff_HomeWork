package com.example.holmi_production.recycleview_4.source.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.source.db.entity.News
import com.example.holmi_production.recycleview_4.source.db.entity.ViewedNews
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface ViewedNewsDao{

    @Query("Select news.newsId, news.theme,news.timeInMilliseconds,viewednews.content FROM News, viewednews WHERE news.newsId == viewednews.id")
    fun getAllViewed(): Flowable<List<News>>

    @Query("Select news.newsId, news.theme,news.timeInMilliseconds,viewednews.content FROM News, viewednews WHERE news.newsId == viewednews.id  Like :newsId ")
    fun getSingleVieved(newsId:Int):Maybe<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(viewedNews: ViewedNews)

}