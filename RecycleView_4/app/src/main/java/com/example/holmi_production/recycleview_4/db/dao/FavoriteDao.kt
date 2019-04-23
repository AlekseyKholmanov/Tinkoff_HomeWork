package com.example.holmi_production.recycleview_4.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("Select * FROM news, favoriteNews WHERE news.newsId == favoriteNews.newsId")
    fun getFavorite(): Flowable<List<News>>

}