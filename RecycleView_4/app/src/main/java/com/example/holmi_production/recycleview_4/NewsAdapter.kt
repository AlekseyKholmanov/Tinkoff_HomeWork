package com.example.holmi_production.recycleview_4

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewsAdapter (val news: ArrayList<News>
): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_item,parent,false)
        return  NewsHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = news[position]
        holder.bind(news)
    }


    class NewsHolder(v:View) : RecyclerView.ViewHolder(v),View.OnClickListener{
        private val theme = v.findViewById<TextView>(R.id.Theme)
        private val date = v.findViewById<TextView>(R.id.Date)
        private val content =  v.findViewById<TextView>(R.id.Content)

        init {
            v.setOnClickListener(this)
        }

        fun bind(news: News){
            theme.text = news.theme
            date.text = news.date
            content.text = news.content
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
    }
}

