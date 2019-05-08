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
import com.example.holmi_production.recycleview_4.source.network.NewsItem
import com.example.holmi_production.recycleview_4.utils.DateUtils

class NewsActivity : MvpAppCompatActivity(), SingleNewsView {



    private var isFavorite: Boolean = false
    var newsId: Int? = null
    private val favoriteIcon = R.drawable.fav_icon
    private val unFavoriteIcon = R.drawable.unfav_icon
    private lateinit var favIcon: MenuItem
    lateinit var content: TextView
    lateinit var date: TextView


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
        favIcon = menu.getItem(0)
        if(isFavorite)
            favIcon.icon = ContextCompat.getDrawable(this, favoriteIcon)
        else
            favIcon.icon = ContextCompat.getDrawable(this, unFavoriteIcon)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        isFavorite = if (isFavorite) {
            singleNewsPresenterImp.deletefromFavorite(newsId!!)
            false
        } else {
            singleNewsPresenterImp.addToFavorite(newsId!!)
            true
        }
        return true
    }

    override fun showNews(listItem: NewsItem) {
        title = listItem.newsHeader.theme
        content.text = HtmlCompat.fromHtml(listItem.content, Html.FROM_HTML_MODE_COMPACT)
        date.text = DateUtils.formatDate(listItem.newsHeader.date.timeInMilliseconds)
    }

    override fun showToast() {
        if (isFavorite)
            Toast.makeText(this, "добавлено $newsId", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "убрано $newsId", Toast.LENGTH_SHORT).show()
    }

    override fun setFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }

    override fun showFavoriteIcon() {
        favIcon.icon = ContextCompat.getDrawable(this, favoriteIcon)
    }

    override fun showUnfavoriteIcon() {
        favIcon.icon = ContextCompat.getDrawable(this, unFavoriteIcon)
    }


}
