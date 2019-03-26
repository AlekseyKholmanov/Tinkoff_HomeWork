package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.utils.DateUtils

class ActivityItem : AppCompatActivity() {


    private var isFavorite: Boolean? = null
    private val favoriteIcon = R.drawable.favorite_enable
    private val nonFavoriteIcon = R.drawable.favorite_none

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        val id = intent.getIntExtra(MainActivity.ARG_ID,0)
        isFavorite = intent.getBooleanExtra(MainActivity.ARG_IS_FAVORITE, false)
        val news = NewsRepository(application).getNewsById(id)

        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)

        title = news.theme
        content.text = news.content
        date.text = DateUtils().formatDate(news.date)
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_item_menu, menu)
        var favoriteIcon = if (isFavorite!!) favoriteIcon else nonFavoriteIcon
        menu.getItem(0).icon = ContextCompat.getDrawable(this, favoriteIcon)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_addFavorite) {
            if (isFavorite!!) {
                item.icon = ContextCompat.getDrawable(this, R.drawable.favorite_none)
                isFavorite = false
                Toast.makeText(this, "убрано", Toast.LENGTH_SHORT).show()
            } else {
                item.icon = ContextCompat.getDrawable(this, R.drawable.favorite_enable)
                isFavorite = true
                Toast.makeText(this, "добавлено", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}