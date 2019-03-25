package com.example.holmi_production.recycleview_4

import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.db.entity.News
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel

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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsViewModel.getAllNews().observe(this, Observer<List<News>> { news ->
            val events = toMap(news)
            var list = setHeader(events)
            news.let {
                val adapter = NewsAdapter(list, callbacks)
                listRecyclerView.adapter = adapter
                listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
        Log.d("tag", "text")
    }


    private fun setHeader(events: Map<Date, List<News>>): ArrayList<ListItem> {
        var news: ArrayList<ListItem> = arrayListOf()
        for (date in events.keys) {
            val header = HeaderItem(date)
            news.add(header)
            for (event in events.getValue(date)) {
                val item = NewsItem(event)
                news.add(item)
            }
        }
        return news
    }

    private fun toMap(events: List<News>?): Map<Date, List<News>> {
        val map = TreeMap<Date, MutableList<News>>()
        if (events != null) {
            for (event in events) {
                var value: MutableList<News>? = map[event.date]
                if (value == null) {
                    value = ArrayList()
                    map[event.date] = value
                }
                value.add(event)
            }
        }
        return map.descendingMap()
    }
}


