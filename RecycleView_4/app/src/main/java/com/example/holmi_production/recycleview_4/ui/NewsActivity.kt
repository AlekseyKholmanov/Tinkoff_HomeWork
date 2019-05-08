package com.example.holmi_production.recycleview_4.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.text.HtmlCompat
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.di.App
import com.example.holmi_production.recycleview_4.mvp.Presenter.SingleNewsPresenterImp
import com.example.holmi_production.recycleview_4.mvp.view.SingleNewsView
import com.example.holmi_production.recycleview_4.source.db.entity.FavoriteNews
import com.example.holmi_production.recycleview_4.source.network.NewsItem
import com.example.holmi_production.recycleview_4.utils.DateUtils

class NewsActivity : MvpAppCompatActivity(), SingleNewsView {

    private var isFavorite: Boolean = false
    var newsId: Int? = null
    private val favoriteIcon = R.drawable.favorite_enable
    private val nonFavoriteIcon = R.drawable.favorite_none
    lateinit var content:TextView
    lateinit var date:TextView


    @InjectPresenter
    lateinit var singleNewsPresenterImp: SingleNewsPresenterImp

    @ProvidePresenter
    fun initPresenter(): SingleNewsPresenterImp {
        return App.mPresenterComponent.singlePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)
        newsId = intent.getIntExtra(MainActivity.ARG_ID, 0)
        content = findViewById(R.id.activity_content)
        date = findViewById(R.id.activity_date)
        initPresenter()
        singleNewsPresenterImp.getSingleNews(newsId!!)
        singleNewsPresenterImp.checkFavorite(newsId!!)
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
            item!!.icon = ContextCompat.getDrawable(
                this,
                R.drawable.favorite_none
            )
            singleNewsPresenterImp.deletefromFavorite(newsId!!)
//            compositeDisposable.add(
//                newsRepository.deleteFavotiteNews(newsId!!)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
//            )
            isFavorite = false
            Toast.makeText(this, "убрано $newsId", Toast.LENGTH_SHORT).show()
        } else {

            singleNewsPresenterImp.addToFavorite(newsId!!)
            item!!.icon = ContextCompat.getDrawable(
                this,
                R.drawable.favorite_enable
            )
            singleNewsPresenterImp.addToFavorite(newsId!!)
            isFavorite = true
            Toast.makeText(this, "добавлено $newsId", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun showNews(newsItem: NewsItem) {
        title = newsItem.newsHeader.theme
        content.text = HtmlCompat.fromHtml(newsItem.content, Html.FROM_HTML_MODE_COMPACT)
        date.text = DateUtils.formatDate(newsItem.newsHeader.date.timeInMilliseconds)
    }

    override fun showToast() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
