package com.example.holmi_production.recycleview_4.di.modules

import android.app.Application
import com.example.holmi_production.recycleview_4.api.NewsService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

@Module
class NetModule(baseUrl:String, application: Application){

    var BASE_URL:String = baseUrl
    val REQUEST_TIMEOUT = 40L

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application):Cache{
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        return Cache(application.cacheDir,cacheSize)
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
            .connectTimeout(REQUEST_TIMEOUT,TimeUnit.SECONDS)
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(retrofit:Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

}