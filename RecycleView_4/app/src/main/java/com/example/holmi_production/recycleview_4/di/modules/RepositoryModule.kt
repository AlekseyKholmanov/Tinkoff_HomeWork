package com.example.holmi_production.recycleview_4.di.modules

import com.example.holmi_production.recycleview_4.Model.RemoteDataSource
import com.example.holmi_production.recycleview_4.db.NewsDatabase
import com.example.holmi_production.recycleview_4.db.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule{

    @Provides
    fun provideRepository(newsDatabase: NewsDatabase,remoteDataSource: RemoteDataSource): NewsRepository {
        return NewsRepository(newsDatabase,remoteDataSource)
    }
}