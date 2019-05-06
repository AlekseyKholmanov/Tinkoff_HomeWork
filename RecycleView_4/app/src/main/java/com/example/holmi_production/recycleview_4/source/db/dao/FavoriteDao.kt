package com.example.holmi_production.recycleview_4.source.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.source.db.entity.News
import io.reactivex.Flowable

@Dao
interface FavoriteDao {

    @Query("Select news.newsId, news.theme,news.timeInMilliseconds,news.content FROM news, favoriteNews WHERE news.newsId == favoriteNews.newsId")
    fun getFavorite(): Flowable<List<News>>

}