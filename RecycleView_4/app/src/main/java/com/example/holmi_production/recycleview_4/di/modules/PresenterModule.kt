package com.example.holmi_production.recycleview_4.di.modules

import com.example.holmi_production.recycleview_4.mvp.Presenter.NewsFragmentPresenterImp
import com.example.holmi_production.recycleview_4.mvp.Presenter.SingleNewsPresenterImp
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import dagger.Module
import dagger.Provides


@Module
class PresenterModule{

    @Provides
    fun provideListPresenter(newsRepository: NewsRepository): NewsFragmentPresenterImp {
        return NewsFragmentPresenterImp(newsRepository)
    }

    @Provides
    fun provideSinglePresenter(newsRepository: NewsRepository):SingleNewsPresenterImp{
        return SingleNewsPresenterImp(newsRepository)
    }
}