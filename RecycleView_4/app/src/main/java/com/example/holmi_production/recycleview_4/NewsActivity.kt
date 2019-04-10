package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class NewsActivity : AppCompatActivity() {


    private var isFavorite: Boolean = false
    var newsId: Int? = null
    private lateinit var newsRepository: NewsRepository
    private val favoriteIcon = R.drawable.favorite_enable
    private val nonFavoriteIcon = R.drawable.favorite_none
    private lateinit var compDelete: Disposable
    private lateinit var compInsert: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        newsId = intent.getIntExtra(MainActivity.ARG_ID, 0)
        newsRepository = NewsRepository(applicationContext)
        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)
        newsRepository.getNewsById(newsId!!)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { it ->
                title = it.theme
                content.text = it.content
                date.text = DateUtils().formatDate(it.date)

            }
            .subscribe()
        newsRepository.getFavoriteNewsById(newsId!!)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { it ->
                isFavorite = it != null

            }
            .subscribe()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_item_menu, menu)
        val favoriteIcon = if (isFavorite) favoriteIcon else nonFavoriteIcon
        menu.getItem(0).icon = ContextCompat.getDrawable(this, favoriteIcon)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val favNews = FavoriteNews(null, newsId!!)

        if (isFavorite) {
            item!!.icon = ContextCompat.getDrawable(this, R.drawable.favorite_none)
            compDelete = newsRepository.deleteFavotiteNews(newsId!!)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    Log.d("TAG1", "delete succes")
                }
                .doOnError {
                    Log.d("TAG1", "delete error")
                }
                .subscribe()
            isFavorite = false
            Toast.makeText(this, "убрано", Toast.LENGTH_SHORT).show()
        } else {
            item!!.icon = ContextCompat.getDrawable(this, R.drawable.favorite_enable)
            compInsert = newsRepository.insertFavoriteNews(favNews)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    Log.d("TAG1", "insert succes")
                }
                .doOnError {
                    Log.d("TAG1", "delete error")
                }
                .subscribe()
            isFavorite = true
            Toast.makeText(this, "добавлено", Toast.LENGTH_SHORT).show()
        }
        RxBus.listen(NewsActivity::class.java).subscribe()
        return true
    }
}
