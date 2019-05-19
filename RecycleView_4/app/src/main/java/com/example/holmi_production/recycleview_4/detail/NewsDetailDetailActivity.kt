package com.example.holmi_production.recycleview_4.detail

import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.holmi_production.recycleview_4.model.ViewedContent
import com.example.holmi_production.recycleview_4.utils.DateUtils
import kotlinx.android.synthetic.main.activity_news_item.*

class NewsDetailDetailActivity : MvpAppCompatActivity(), NewsDetailView {
    override fun showDetails(details: ViewedContent,isFavorite: Boolean) {
        activity_content.text = details.viewedContent
        this.isFavorite = isFavorite
    }

    override fun showFavoriteChangedToast(isFavorite: Boolean) {
        val message: Int = if ( isFavorite) {
            R.string.added_to_favourite
        } else {
            R.string.removed_from_favourite
        }
        Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT).show()
    }

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

        activity_theme.text = intent.getStringExtra(ARG_Theme)
        activity_date.text = intent.getLongExtra(ARG_DATE,0L).toString()

        initPresenter()
        newsDetailPresenter.getSingleNews(intent.getStringExtra(ARG_ID))
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
        newsDetailPresenter.changeFavoriteState(isFavorite)
        invalidateOptionsMenu()
        return true
    }

    override fun showToast() {
        if (isFavorite)
            Toast.makeText(this, "добавлено $newsId", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "убрано $newsId", Toast.LENGTH_SHORT).show()
    }

}
