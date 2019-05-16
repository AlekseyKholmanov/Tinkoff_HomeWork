package com.example.holmi_production.recycleview_4.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.holmi_production.recycleview_4.NewsItems.HeaderContainer
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.NewsItems.NewsType
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.model.News
import com.example.holmi_production.recycleview_4.utils.DateUtils


class NewsAdapter(
    private var newsContainer: List<NewsContainer> = listOf(),
    private var clickOnNewsCallback: ClickOnNewsCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            NewsContainer.TYPE_HEADER -> {
                val itemView = inflater.inflate(R.layout.view_list_item_header, parent, false)
                HeaderViewHolder(itemView)
            }
            NewsContainer.TYPE_NEWS -> {
                val itemView = inflater.inflate(R.layout.view_list_item_news, parent, false)
                NewsViewHolder(itemView)
            }
            else -> throw IllegalStateException("unsupported item type")
        }
    }

    fun setNews(news: List<NewsContainer>) {
        this.newsContainer = news
    }

    override fun getItemCount(): Int = newsContainer.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val currentDay = DateUtils.currentDay
        when (viewType) {
            NewsContainer.TYPE_HEADER -> {
                val headerItem = newsContainer[position] as HeaderContainer
                val holder = viewHolder as HeaderViewHolder
                //TODO вынести отображение даты в Utils
                val dateText = DateUtils.convertToDate(headerItem.date)
                val headerDate: String
                headerDate = when (dateText) {
                    DateUtils.buildDate(currentDay) -> {
                        "Сегодня"
                    }
                    DateUtils.buildDate(currentDay - 1) -> {
                        "Вчера"
                    }
                    else -> {
                        DateUtils.formatDate(headerItem.date)
                    }
                }
                holder.txt_header.text = headerDate
            }
            NewsContainer.TYPE_NEWS -> {
                val newsItem = newsContainer[position] as NewsType
                val viewHolder1 = viewHolder as NewsViewHolder
                viewHolder1.bind(newsItem.content, clickOnNewsCallback)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return newsContainer[position].getType()
    }

    class NewsViewHolder internal constructor(var v: View) : RecyclerView.ViewHolder(v) {

        private val theme = v.findViewById<TextView>(R.id.theme)
        private var date = ""

        fun bind(
            news: News,
            clickOnNewsCallback: ClickOnNewsCallback
        ) {
            theme.text = news.theme
            date = DateUtils.formatDate(news.date.timeInMilliseconds)
            v.setOnClickListener {
                clickOnNewsCallback.onItemClicked(news)
            }
        }
    }

    class HeaderViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_header: TextView = itemView.findViewById(R.id.txt_header)

    }
}

