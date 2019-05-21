package com.example.holmi_production.recycleview_4.detail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.App
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.model.NewsItemDetails
import kotlinx.android.synthetic.main.activity_news_item.*

class NewsDetailDetailActivity : MvpAppCompatActivity(), NewsDetailView {

    private var isFavorite: Boolean = false
    var newsId: Int? = null

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
        activity_date.text = intent.getLongExtra(ARG_DATE, 0L).toString()

        initPresenter()
        newsDetailPresenter.getSingleNews(intent.getStringExtra(ARG_ID))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_addFavorite).apply {
            val iconPic = if (isFavorite) R.drawable.fav_icon else R.drawable.unfav_icon
            setIcon(iconPic)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        isFavorite = !isFavorite
        newsDetailPresenter.changeFavoriteState(isFavorite)
        invalidateOptionsMenu()
        return true
    }

    override fun showError(error: Throwable) {
        Snackbar.make(scrollView, R.string.error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showDetails(details: NewsItemDetails, isFavorite: Boolean) {
        activity_content.text = details.content
        activity_date.text = details.title.date.timeInMilliseconds.toString()
        activity_theme.text = details.title.theme
        this.isFavorite = isFavorite
        invalidateOptionsMenu()
    }

    override fun showFavoriteChangedToast(isFavorite: Boolean) {
        val message: Int = if (isFavorite) {
            R.string.added_to_favourite
        } else {
            R.string.removed_from_favourite
        }
        Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        val ARG_ID = "id"
        val ARG_Theme = "theme"
        val ARG_DATE = "date"
    }
}
