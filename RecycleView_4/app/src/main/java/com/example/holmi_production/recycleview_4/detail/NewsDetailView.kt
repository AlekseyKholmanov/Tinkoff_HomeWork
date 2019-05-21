package com.example.holmi_production.recycleview_4.detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.holmi_production.recycleview_4.model.NewsItem
import com.example.holmi_production.recycleview_4.model.ViewedContent

interface NewsDetailView : MvpView {

    fun showFavoriteChangedToast(isFavorite: Boolean)

    fun showDetails(details:ViewedContent, isFavorite: Boolean)

    fun showError(error: Throwable)
}