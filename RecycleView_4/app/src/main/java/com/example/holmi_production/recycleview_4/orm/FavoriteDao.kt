package com.example.holmi_production.recycleview_4.orm

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.holmi_production.recycleview_4.model.NewsItemTitle
import io.reactivex.Flowable

@Dao
interface FavoriteDao {

    @Query("Select newsitemtitle.newsId, newsitemtitle.theme,newsitemtitle.timeInMilliseconds FROM newsitemtitle, favoriteNews WHERE newsitemtitle.newsId == favoriteNews.newsId")
    fun getFavorite(): Flowable<List<NewsItemTitle>>

}