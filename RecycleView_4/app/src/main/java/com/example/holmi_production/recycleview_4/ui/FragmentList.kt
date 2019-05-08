package com.example.holmi_production.recycleview_4.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.di.App
import com.example.holmi_production.recycleview_4.mvp.Presenter.NewsFragmentPresenterImp
import com.example.holmi_production.recycleview_4.mvp.view.ListNewsView
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class FragmentList : MvpAppCompatFragment(), ClickOnNewsCallback,
    ListNewsView, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val ARG_FAVORITE = "isFavorite"
        @JvmStatic
        fun newInstance(isFavorite: Boolean): FragmentList {
            val args = Bundle()
            args.putBoolean(ARG_FAVORITE, isFavorite)
            val fragment = FragmentList()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mAdapter: NewsAdapter
    private var isFavorite: Boolean? = null

    @InjectPresenter
    lateinit var newsFragmentPresenterImp: NewsFragmentPresenterImp
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @ProvidePresenter
    fun initPresenter(): NewsFragmentPresenterImp {
        return App.mPresenterComponent.listPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout = rootView.findViewById(R.id.fragment_list)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        if (isFavorite!!)
            mSwipeRefreshLayout.isEnabled = false
        return rootView
    }

    override fun onActivityCreated(bundle: Bundle?) {
        mAdapter = NewsAdapter(clickOnNewsCallback = this as ClickOnNewsCallback)
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        getNews()
        super.onActivityCreated(bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
    }

    override fun onRefresh() {
        newsFragmentPresenterImp.updateNews(isFavorite!!)
    }

    override fun showRefreshingStart() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun showRefreshingEnd() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun showFavoriteNews(news: ArrayList<NewsContainer>) {
        mAdapter.setNews(news)
        mAdapter.notifyDataSetChanged()
    }

    private fun getNews() {
        newsFragmentPresenterImp.getNews(isFavorite!!)
    }

    override fun onItemClicked(newsId: Int) {
        newsFragmentPresenterImp.openSingleNews(newsId)
    }

    override fun showNetworkAlertDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNews(news: ArrayList<NewsContainer>) {
        mAdapter.setNews(news)
        mAdapter.notifyDataSetChanged()
    }

    override fun showSingleNews(newsId: Int) {
        val intent = Intent(context, NewsActivity::class.java).apply {
            putExtra(MainActivity.ARG_ID, newsId)
        }
        ContextCompat.startActivity(context!!, intent, null)
    }

    override fun updateListNews() {
        mAdapter.notifyDataSetChanged()
    }
}

interface ClickOnNewsCallback {
    fun onItemClicked(newsId: Int)
}




