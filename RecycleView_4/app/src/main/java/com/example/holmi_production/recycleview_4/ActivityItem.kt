package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class ActivityItem: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_item)

        val theme_text  = intent.getStringExtra("theme")
        val content_text = intent.getStringExtra("content")
        val date_text = intent.getStringExtra("date")

        val theme = findViewById<TextView>(R.id.activity_theme)
        val content = findViewById<TextView>(R.id.activity_content)
        val date = findViewById<TextView>(R.id.activity_date)

        theme.text = theme_text
        content.text = content_text
        date.text=date_text
    }
}