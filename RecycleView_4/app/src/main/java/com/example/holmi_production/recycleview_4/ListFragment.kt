package com.example.holmi_production.recycleview_4

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast
import com.example.holmi_production.recycleview_4.utils.DateUtils
import java.util.stream.Collectors.toMap


class ListFragment : Fragment() {
    interface Callbacks {
        fun onItemClicked(v: View, news: News)
    }
    companion object {
        private const val ARG_NAME = "isFavorite"
        @JvmStatic
        fun newInstance(isFavorite: Boolean) : ListFragment {
            val args = Bundle()
            args.putBoolean(ARG_NAME, isFavorite)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }
    lateinit var mAdapter: NewsAdapter


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = requireActivity() as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private lateinit var newsRepository: NewsRepository
    private var callbacks: Callbacks? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list: ArrayList<ListItem>
        newsRepository = NewsRepository(activity!!.applicationContext)
        val isFav = arguments?.getBoolean(ARG_NAME)
        list = arrayList(isFav)
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter = NewsAdapter(list, callbacks)
        listRecyclerView.adapter = mAdapter
    }

    private fun arrayList(
        isFav: Boolean?
    ): ArrayList<ListItem> {
        var newsItems: ArrayList<ListItem>
        if (!isFav!!) {
            var news = newsRepository.getAllNews()
            newsItems = DateUtils().reformateItem(news)

        } else {
            val fNews = newsRepository.getAllFavoriteNews()
            var items = arrayListOf<News>()
            for (i in fNews) {
                val item = newsRepository.getNewsById(i.newsId)
                items.add(item)
            }
            newsItems = DateUtils().reformateItem(items)

        }
        return newsItems
    }


    override fun onResume() {
        super.onResume()
        val isFav = arguments?.getBoolean(ARG_NAME)
        if(!isFav!!){
            return
        }
        val list = arrayList(isFav)
        val adapter = NewsAdapter(list,callbacks)
        listRecyclerView.adapter = adapter
    }
}


