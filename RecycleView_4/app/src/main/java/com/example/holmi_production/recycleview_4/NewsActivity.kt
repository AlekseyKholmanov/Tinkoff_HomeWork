package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.text.HtmlCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class NewsActivity : AppCompatActivity() {


    private var isFavorite: Boolean = false
    var newsId: Int? = null
    private lateinit var newsRepository: NewsRepository
    private val favoriteIcon = R.drawable.favorite_enable
    private val nonFavoriteIcon = R.drawable.favorite_none
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        newsId = intent.getIntExtra(MainActivity.ARG_ID, 0)
        newsRepository = NewsRepository(applicationContext)
        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)
        compositeDisposable.add(newsRepository.getNewsFromNetworkById(newsId!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                title = it.newsItem.newsHeader.theme
                content.text = HtmlCompat.fromHtml(it.newsItem.content, Html.FROM_HTML_MODE_COMPACT)
                date.text = DateUtils().formatDate(it.newsItem.newsHeader.date.timeInMilliseconds)
            })
        compositeDisposable.add(
            newsRepository.getFavoriteNewsById(newsId!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    isFavorite = it != null
                })
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
            compositeDisposable.add(
                newsRepository.deleteFavotiteNews(newsId!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError { Log.d("qwerty1", "error $it") }
                    .subscribe()
            )
            isFavorite = false
            Toast.makeText(this, "убрано $newsId", Toast.LENGTH_SHORT).show()
        } else {
            item!!.icon = ContextCompat.getDrawable(this, R.drawable.favorite_enable)
            compositeDisposable.add(
                newsRepository.insertFavoriteNews(favNews)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError { Log.d("qwerty1", "error $it") }
                    .subscribe()
            )
            isFavorite = true
            Toast.makeText(this, "добавлено $newsId", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
