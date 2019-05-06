package com.example.holmi_production.recycleview_4.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.di.App
import com.example.holmi_production.recycleview_4.mvp.Presenter.NewsListPresenter
import com.example.holmi_production.recycleview_4.mvp.view.NewsListView
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class ListFragment : MvpAppCompatFragment(), ClickOnNewsCallback,
    NewsListView {

    companion object {
        private const val ARG_FAVORITE = "isFavorite"
        @JvmStatic
        fun newInstance(isFavorite: Boolean): ListFragment {
            val args = Bundle()
            args.putBoolean(ARG_FAVORITE, isFavorite)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenter

    private lateinit var mAdapter: NewsAdapter
    private var isFavorite: Boolean? = null

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

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        mAdapter = NewsAdapter(clickOnNewsCallback = this as ClickOnNewsCallback)
        setNewsToAdapter()
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
        super.onCreate(savedInstanceState)
        returnPresenter()
    }

    private fun setNewsToAdapter() {
        newsListPresenter.getNews(isFavorite!!)
    }

    override fun onItemClicked(newsId: Int) {
        newsListPresenter.openSingleNews(newsId)
    }

    override fun showNetworkAlertDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNews(news: ArrayList<ListItem>) {
        mAdapter.setNews(news)
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

    @ProvidePresenter
    fun returnPresenter(): NewsListPresenter {
        return App.mPresenterComponent.presenter()
    }
}

interface ClickOnNewsCallback {
    fun onItemClicked(newsId: Int)
}




