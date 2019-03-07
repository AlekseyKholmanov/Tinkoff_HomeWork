package com.example.holmi_production.recycleview_4

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ListFragment : Fragment() {
    var news: ArrayList<News> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        initializeNews()
        val adapter = NewsAdapter(news)
        recyclerView.adapter = adapter
        return view

    }

    private fun initializeNews() {
        val string = resources.getString(R.string.lorem)
        for (i in 0 until 15) {
            news.add(0, News("$i. Why is Lorem?", "$i-09-2018", string))
        }
    }
}