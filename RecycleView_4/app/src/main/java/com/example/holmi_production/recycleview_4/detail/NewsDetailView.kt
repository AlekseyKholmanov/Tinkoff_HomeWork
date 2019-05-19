package com.example.holmi_production.recycleview_4.detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.holmi_production.recycleview_4.model.NewsItem
import com.example.holmi_production.recycleview_4.model.ViewedContent

interface NewsDetailView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showToast()

    @StateStrategyType(SkipStrategy::class)
    fun showFavoriteChangedToast(isFavorite:Boolean)

    fun showDetails(details:ViewedContent, isFavorite: Boolean)
}