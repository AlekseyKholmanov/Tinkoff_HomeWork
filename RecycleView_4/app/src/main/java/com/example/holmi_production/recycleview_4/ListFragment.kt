package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ListFragment: Fragment(){
    var news: ArrayList<News> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_item, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.listRecycleView)
        initializeNews()
        val adapter = NewsAdapter(news)
        recycleView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recycleView.layoutManager = layoutManager
        return view

    }

    private fun initializeNews() {
        news.add(0, News("theme1","22-09-2018","content1"))
        news.add(1,News("theme2","23-09-2018","content2"))
        news.add(2,News("theme3","24-09-2018","content3"))
    }
}