package com.example.holmi_production.recycleview_4

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewsAdapter(_news: ArrayList<News>, _isFavorite: Boolean) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    private var news = _news
    private var isFavorite = _isFavorite


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_item, parent, false)
        news = if (isFavorite) news.filter { !it.isFavorites } as ArrayList<News> else news
        return NewsHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = news[position]
        holder.bind(news)
    }


    class NewsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val theme = v.findViewById<TextView>(R.id.Theme)
        private val date = v.findViewById<TextView>(R.id.Date)
        private val content = v.findViewById<TextView>(R.id.Content)
        private var isFavorite:Boolean = false

        init {
            v.setOnClickListener(this)
        }

        fun bind(news: News) {
            theme.text = news.theme
            date.text = news.date
            content.text = news.content
            isFavorite = news.isFavorites
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context, ActivityItem::class.java).apply{
                putExtra("theme",theme.text)
                putExtra("content", content.text)
                putExtra("date",date.text)
                putExtra("isFavorite", isFavorite)
            }
            startActivity(v.context,intent,null)
            Log.d("RecyclerView", "CLICK!")
        }
    }
}

