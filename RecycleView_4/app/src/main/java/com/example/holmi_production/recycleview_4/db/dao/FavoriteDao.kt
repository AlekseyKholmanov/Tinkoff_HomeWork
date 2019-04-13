package com.example.holmi_production.recycleview_4.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.db.entity.News
import io.reactivex.Single

@Dao
interface FavoriteDao{

    @Query("Select news.id, news.theme, news.date, news.content FROM news, favoriteNews WHERE news.id== favoriteNews.newsId")
    fun getFavorite():Single<List<News>>

}