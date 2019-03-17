package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class ActivityItem : AppCompatActivity() {


    private var isFavorite: Boolean? = null
    private val favoriteIcon = R.drawable.favorite_enable
    private val nonFavoriteIcon = R.drawable.favorite_none

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        val themeText = intent.getStringExtra(MainActivity.ARG_THEME)
        val contentText = intent.getStringExtra(MainActivity.ARG_CONTENT)
        val dateText = intent.getStringExtra(MainActivity.ARG_DATE)
        isFavorite = intent.getBooleanExtra(MainActivity.ARG_IS_FAVORITE, false)

        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)

        title = themeText
        content.text = contentText
        date.text = dateText
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