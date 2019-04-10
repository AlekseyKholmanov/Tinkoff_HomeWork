package com.example.holmi_production.recycleview_4.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.Converter.DateConverter
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils

@Database(entities = [News::class, FavoriteNews::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun favoriteNewsDao(): FavoriteNewsDao


    companion object {

        private val DATABASE_NAME = "basic-sample-db"
        var sInstance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase? {
            if (sInstance == null) {
                synchronized(NewsDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            NewsDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(NewsDatabaseCallback())
                            .build()
                    }
                }
            }
            return sInstance
        }
    }

    private class NewsDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            PopulateDbAsynkTask(sInstance!!).execute()
        }
    }

    private class PopulateDbAsynkTask(newsDatabase: NewsDatabase) : AsyncTask<Void, Void, Void>() {
        private val newsDao: NewsDao = newsDatabase.newsDao()
        private val favoriteNewsDao = newsDatabase.favoriteNewsDao()

        override fun doInBackground(vararg params: Void): Void? {
            populateDatabase(newsDao, favoriteNewsDao)
            return null
        }

        fun populateDatabase(
            newsDao: NewsDao,
            favoriteNewsDao: FavoriteNewsDao
        ) {
            newsDao.deleteAll()
            favoriteNewsDao.deleteAll()
            for (i in 0..25) {
                var news = News(
                    i,
                    "Theme $i",
                    DateUtils().buildDate(i),
                    "Also note that the whole language dependency can be taken care of by the android framework. " +
                            "Simply create different folders for each language. If english is your default language, " +
                            "just put the english strings into res/values/strings.xml. Then create a new folder values-ru and put the russian strings " +
                            "with identical names into res/values-ru/strings.xml. From this point on android  $i"
                )
                newsDao.insert(news)
            }
            favoriteNewsDao.insert(FavoriteNews(null,24))
            favoriteNewsDao.insert(FavoriteNews(null,20))
            favoriteNewsDao.insert(FavoriteNews(null, 8))
        }
    }

}
