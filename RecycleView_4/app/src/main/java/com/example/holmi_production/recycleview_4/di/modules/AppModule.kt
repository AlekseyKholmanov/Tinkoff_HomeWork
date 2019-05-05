package com.example.holmi_production.recycleview_4.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application){

    @Provides
    @Singleton
    fun provideApplication(): Application = application
}