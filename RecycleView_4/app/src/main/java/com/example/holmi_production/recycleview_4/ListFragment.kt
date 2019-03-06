package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ListFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_item, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.listRecycleView)
        recycleView.adapter
        return view

    }
}