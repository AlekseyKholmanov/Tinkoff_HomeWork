package com.example.holmi_production.recycleview_4.di.modules

import android.app.Application
import com.example.holmi_production.recycleview_4.Model.RemoteDataSource
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

@Module
class NetModule(baseUrl:String, application: Application){

    var mBaseUrl:String = baseUrl
    val mApplication = application
    @Provides
    @Singleton
    fun provideOkHttpCache():Cache{
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        return Cache(mApplication.cacheDir,cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson():Gson{
        val gsonBuilder = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRemoteDataSource(retrofit:Retrofit): RemoteDataSource {
        return retrofit.create(RemoteDataSource::class.java)
    }

}