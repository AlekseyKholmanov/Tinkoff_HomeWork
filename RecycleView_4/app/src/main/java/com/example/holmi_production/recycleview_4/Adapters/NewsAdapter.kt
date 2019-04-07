package com.example.holmi_production.recycleview_4.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holmi_production.recycleview_4.ListFragment.ClickOnNewsCallback
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.utils.DateUtils


class NewsAdapter(
    private val listItem: List<ListItem>?,
    private var clickOnNewsCallback: ClickOnNewsCallback?
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

    override fun getItemCount(): Int = listItem!!.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var viewType = getItemViewType(position)
        val currentDay = DateUtils.currentDay
        when (viewType) {
            ListItem.TYPE_HEADER -> {
                var headerItem = listItem?.get(position) as HeaderItem
                val viewHolder = viewHolder as HeaderViewHolder
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
                viewHolder.txt_header.text = dateText
            }
            ListItem.TYPE_NEWS -> {
                var newsItem = listItem?.get(position) as NewsItem
                val viewHolder1 = viewHolder as NewsViewHolder

                viewHolder1.bind(newsItem.content, clickOnNewsCallback)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listItem?.get(position)!!.getType()
    }
}

