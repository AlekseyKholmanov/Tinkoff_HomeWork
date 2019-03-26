//package com.example.holmi_production.recycleview_4
//
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import com.example.holmi_production.recycleview_4.db.NewsRepository
//import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
//import com.example.holmi_production.recycleview_4.db.entity.News
//
//public class NewsViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: NewsRepository = NewsRepository(application)
//    private val allNews: LiveData<List<News>>
//    private  val allFavoriteNews:LiveData<List<FavoriteNews>>
//
//    init {
//        allNews = repository.getAllNews()
//        allFavoriteNews = repository.getAllFavoriteNews()
//    }
//
//    fun insert(news: News) {
//        repository.insert(news)
//    }
//
//    fun deleteAll() {
//        repository.deleteAll()
//    }
//
//    fun getNewsById(id: Int){
//        repository.getNewsById(id)
//    }
//
//    fun getAllNews(): LiveData<List<News>> {
//        return allNews
//    }
//
//    fun getAllFavoriteNews(): LiveData<List<FavoriteNews>> {
//       return allFavoriteNews
//    }
//
//}