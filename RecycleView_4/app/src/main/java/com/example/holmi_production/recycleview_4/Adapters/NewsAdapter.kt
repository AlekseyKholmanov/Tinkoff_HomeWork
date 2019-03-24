package com.example.holmi_production.recycleview_4.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.holmi_production.recycleview_4.ListFragment.Callbacks
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.utils.DateUtils

class NewsAdapter(
    private val listItem: List<ListItem>,
    private var callbacks: Callbacks?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ListItem.TYPE_HEADER -> {
                val itemView = inflater.inflate(R.layout.view_list_item_header, parent, false)
                HeaderViewHolder(itemView)
            }
            ListItem.TYPE_NEWS -> {
                val itemView = inflater.inflate(R.layout.view_list_item_news, parent, false)
                NewsViewHolder(itemView)
            }
            else -> throw IllegalStateException("unsupported item type")
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var viewType = getItemViewType(position)
        val currentDay = DateUtils.currentDay
        when (viewType) {
            ListItem.TYPE_HEADER -> {
                var headerItem = listItem[position] as HeaderItem
                val viewHolder1 = viewHolder as HeaderViewHolder
                var dateText = when (headerItem.date) {
                    DateUtils().buildDate(currentDay) -> {
                        "Сегодня"
                    }
                    DateUtils().buildDate(currentDay - 1) -> {
                        "Вчера"
                    }
                    else -> {
                        DateUtils().formatDate(headerItem.date)
                    }
                }
                viewHolder1.txt_header.text = dateText
            }
            ListItem.TYPE_NEWS -> {
                var newsItem = listItem[position] as NewsItem
                val viewHolder1 = viewHolder as NewsViewHolder

                viewHolder1.bind(newsItem.content, callbacks)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listItem[position].getType()
    }

    private class HeaderViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_header: TextView = itemView.findViewById(R.id.txt_header)

    }

    private class NewsViewHolder internal constructor(var v: View) : RecyclerView.ViewHolder(v) {

        private val theme = v.findViewById<TextView>(R.id.theme)
        private val content = v.findViewById<TextView>(R.id.content)
        private var date = ""
        private var isFavorite = false

        fun bind(
            news: News,
            callbacks: Callbacks?
        ) {
            theme.text = news.theme
            date = DateUtils().formatDate(news.date)
            content.text = news.content
            isFavorite = news.isFavorites
            v.setOnClickListener {
                callbacks?.onItemClicked(v, news)
            }
        }
    }
}

