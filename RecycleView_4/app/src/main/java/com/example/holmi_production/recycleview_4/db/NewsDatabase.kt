package com.example.holmi_production.recycleview_4.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import com.example.holmi_production.recycleview_4.db.dao.FavoriteNewsDao
import com.example.holmi_production.recycleview_4.db.dao.NewsDao
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils

@Database(entities = [News::class, FavoriteNews::class], version = 1)
public abstract class NewsDatabase : RoomDatabase() {

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
            PopulateDbAsynkTask(sInstance).execute()
        }
    }

    private class PopulateDbAsynkTask(newsDatabase: NewsDatabase?) : AsyncTask<Void, Void, Void>() {
        private val newsDao: NewsDao = newsDatabase!!.newsDao()

        override fun doInBackground(vararg params: Void?): Void? {
            populateDatabase(newsDao)
            return null
        }

        fun populateDatabase(newsDao: NewsDao) {
            newsDao.deleteAll()
            for (i in 0..25) {
                var news = News(
                    i,
                    "Theme $i",
                    DateUtils().buildDate(i),
                    "text $i",
                    false
                )
                newsDao.insert(news)
            }
        }
    }
}
