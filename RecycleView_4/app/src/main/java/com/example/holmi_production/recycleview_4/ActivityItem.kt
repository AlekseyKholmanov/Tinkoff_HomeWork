package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.TextView

class ActivityItem: AppCompatActivity() {

    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        val themeText  = intent.getStringExtra("theme")
        val contentText = intent.getStringExtra("content")
        val dateText = intent.getStringExtra("date")
        isFavorite = intent.getBooleanExtra("isFavorite",false)
        val theme = findViewById<TextView>(R.id.activity_theme)
        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)

        theme.text = themeText
        content.text = contentText
        date.text=dateText
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_item_menu,menu)
        if(!isFavorite)
            menu!!.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.favorite_none)
        else
            menu!!.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.favorite_enable)
        return true
    }
}