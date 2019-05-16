package com.example.holmi_production.recycleview_4.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.text.HtmlCompat
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.App
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.model.NewsItem
import com.example.holmi_production.recycleview_4.utils.DateUtils
import kotlinx.android.synthetic.main.activity_news_item.*

class NewsDetailDetailActivity : MvpAppCompatActivity(), NewsDetailView {
companion object{
    val ARG_ID = "id"
    val ARG_Theme = "theme"
    val ARG_DATE = "date"
}


    private var isFavorite: Boolean = false
    var newsId: Int? = null
    private val favoriteIcon = R.drawable.fav_icon
    private val unFavoriteIcon = R.drawable.unfav_icon
    private lateinit var favIcon: MenuItem


    @InjectPresenter
    lateinit var newsDetailPresenter: NewsDetailPresenter

    @ProvidePresenter
    fun initPresenter(): NewsDetailPresenter {
        return App.component.getNewsItemDetailsPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)
        newsId = intent.getIntExtra(ARG_ID, 0)

        val newsTheme  = intent.getStringExtra(ARG_Theme)
        val newsDate = intent.getLongExtra(ARG_DATE,0L)

        activity_theme.text = newsTheme
        activity_date.text = newsDate.toString()

        initPresenter()
        newsDetailPresenter.getSingleNews(newsId!!)
        newsDetailPresenter.checkFavorite(newsId!!)
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
            newsDetailPresenter.deletefromFavorite(newsId!!)
            false
        } else {
            newsDetailPresenter.addToFavorite(newsId!!)
            true
        }
        return true
    }

    override fun showNews(listItem: NewsItem) {
        title = listItem.newsHeader.theme
        activity_content.text = HtmlCompat.fromHtml(listItem.content, Html.FROM_HTML_MODE_COMPACT)
        activity_date.text = DateUtils.formatDate(listItem.newsHeader.date.timeInMilliseconds)
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
