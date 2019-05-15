package com.example.holmi_production.recycleview_4.di.modules

import com.example.holmi_production.recycleview_4.api.RemoteDataSource
import com.example.holmi_production.recycleview_4.orm.NewsDatabase
import com.example.holmi_production.recycleview_4.model.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule{

    @Provides
    @Singleton
    fun provideRepository(newsDatabase: NewsDatabase, remoteDataSource: RemoteDataSource): NewsRepository {
        return NewsRepository(newsDatabase, remoteDataSource)
    }
}