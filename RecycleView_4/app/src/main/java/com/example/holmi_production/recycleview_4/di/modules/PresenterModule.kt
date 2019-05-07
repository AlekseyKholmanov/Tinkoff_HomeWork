package com.example.holmi_production.recycleview_4.di.modules

import com.example.holmi_production.recycleview_4.di.scope.PresenterScope
import com.example.holmi_production.recycleview_4.mvp.Presenter.ListNewsPresenter
import com.example.holmi_production.recycleview_4.mvp.Presenter.SingleNewsPresenter
import com.example.holmi_production.recycleview_4.mvp.model.NewsRepository
import dagger.Module
import dagger.Provides


@Module
class PresenterModule{

    @Provides
    @PresenterScope
    fun provideListPresenter(newsRepository: NewsRepository): ListNewsPresenter {
        return ListNewsPresenter(newsRepository)
    }

    @Provides
    @PresenterScope
    fun provideSinglePresenter(newsRepository: NewsRepository):SingleNewsPresenter{
        return SingleNewsPresenter(newsRepository)
    }
}