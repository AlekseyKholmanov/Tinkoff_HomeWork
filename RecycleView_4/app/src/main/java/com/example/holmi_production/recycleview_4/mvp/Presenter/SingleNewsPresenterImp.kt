package com.example.holmi_production.recycleview_4.mvp.Presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import com.example.holmi_production.recycleview_4.mvp.view.SingleNewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SingleNewsPresenterImp @Inject constructor(private val newsRepository: NewsRepository) :
    MvpPresenter<SingleNewsView>(), SingleNewsPresenter {

    private val compositeDisposable = CompositeDisposable()
    override fun checkFavorite(newsId: Int) {

    }

    override fun deletefromFavorite(newsId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addToFavorite(newsId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSingleNews(newsId: Int) {
        compositeDisposable.add(
            newsRepository.getNewsFromNetworkById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { listItem ->
                    viewState.showNews(listItem.listNews)
                })
    }
}