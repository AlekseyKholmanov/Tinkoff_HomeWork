package com.example.holmi_production.recycleview_4.detail


import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.recycleview_4.async
import com.example.holmi_production.recycleview_4.mvp.BasePresenter
import com.example.holmi_production.recycleview_4.storage.NewsDetailsRepository
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

@InjectViewState
class NewsDetailPresenter @Inject constructor(
    private val repository: NewsDetailsRepository
) :
    BasePresenter<NewsDetailView>() {

    private lateinit var newsItemId: String

    fun changeFavoriteState(isFavorite: Boolean) {
        repository.changeFavoriteState(newsItemId, isFavorite)
            .async()
            .subscribe {
                viewState.showFavoriteChangedToast(isFavorite)
            }
            .keep()
    }

    fun getSingleNews(newsId: String) {
        this.newsItemId = newsId
        Singles.zip(
            repository.getViewedNewsById(newsId),
            repository.isFavorite(newsId)
        )
            .async()
            .subscribe ({ (t1, isFavorite) ->
                viewState.showDetails(t1, isFavorite)
            },{error->
                viewState.showError(error)
            })
            .keep()
    }
}