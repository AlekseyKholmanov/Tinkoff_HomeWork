package com.example.holmi_production.recycleview_4.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.holmi_production.recycleview_4.ListFragment
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils

class NewsViewHolder internal constructor(var v: View) : RecyclerView.ViewHolder(v) {

    private val theme = v.findViewById<TextView>(R.id.theme)
    private val content = v.findViewById<TextView>(R.id.content)
    private var date = ""

    fun bind(
        news: News,
        callbacks: ListFragment.Callbacks?
    ) {
        theme.text = news.theme
        date = DateUtils().formatDate(news.date)
        content.text = news.content
        v.setOnClickListener {
            callbacks?.onItemClicked(v, news)
        }
    }
}
 class HeaderViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var txt_header: TextView = itemView.findViewById(R.id.txt_header)

}