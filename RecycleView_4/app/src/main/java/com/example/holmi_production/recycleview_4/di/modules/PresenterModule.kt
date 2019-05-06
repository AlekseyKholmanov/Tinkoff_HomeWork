package com.example.holmi_production.recycleview_4.di.modules

import com.example.holmi_production.recycleview_4.di.scope.PresenterScope
import com.example.holmi_production.recycleview_4.mvp.Presenter.NewsListPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresenterModule{

    @Provides
    @PresenterScope
    fun providePresenter(newsRepository: NewsRepository): NewsListPresenter {
        return NewsListPresenter(newsRepository)
    }
}