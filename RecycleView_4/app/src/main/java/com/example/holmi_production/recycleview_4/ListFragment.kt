package com.example.holmi_production.recycleview_4

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.utils.DateUtils
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment() {

    interface Callbacks {
        fun onItemClicked(v: View, news: News)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = requireActivity() as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private var news: ArrayList<ListItem> = arrayListOf()


    private var callbacks: Callbacks? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val isFav = if (arguments == null) false else arguments!!.getBoolean(MainActivity.ARG_IS_FAVORITE)
        val events = toMap(loadNews(isFav))
        setHeader(events)

        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val adapter = NewsAdapter(news, callbacks)
        recyclerView.adapter = adapter
        return view
    }

    private fun setHeader(events: Map<Date, List<News>>) {
        for (date in events.keys) {
            val header = HeaderItem(date)
            news.add(header)
            for (event in events.getValue(date)) {
                val item = NewsItem(event)
                news.add(item)
            }
        }
    }

    private fun loadNews(isFav: Boolean): List<News> {
        val events = ArrayList<News>()
        val content = resources.getString(R.string.lorem)
        for (i in 1..20) {
            ///Хак для новостей на вкладке избранное
            var news = News(i,
                "Why is lorem theme $i ?",
                buildRandomDateInCurrentMonth(),
                content,
                i % 2 == 0
            )
            if (isFav) {
                if (i % 2 == 0)
                    events.add(news)
                continue
            } else events.add(news)
        }
        return events
    }

    private fun buildRandomDateInCurrentMonth(): Date {
        val random = Random()
        val todayDay = DateUtils.currentDay
        return DateUtils().buildDate(random.nextInt(todayDay) + 1)
    }

    private fun toMap(events: List<News>): Map<Date, List<News>> {
        val map = TreeMap<Date, MutableList<News>>()
        for (event in events) {
            var value: MutableList<News>? = map[event.date]
            if (value == null) {
                value = ArrayList()
                map[event.date] = value
            }
            value.add(event)
        }
        return map.descendingMap()
    }
}


