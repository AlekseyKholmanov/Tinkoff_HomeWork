package com.example.holmi_production.recycleview_4.detail

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.recycleview_4.model.NewsItemDetails

interface NewsDetailView : MvpView {

    fun showFavoriteChangedToast(isFavorite: Boolean)

    fun showDetails(details:NewsItemDetails, isFavorite: Boolean)

    fun showError(error: Throwable)
}