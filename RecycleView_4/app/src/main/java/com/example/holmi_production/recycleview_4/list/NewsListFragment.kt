package com.example.holmi_production.recycleview_4.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.App
import com.example.holmi_production.recycleview_4.MainActivity
import com.example.holmi_production.recycleview_4.detail.NewsDetailDetailActivity
import com.example.holmi_production.recycleview_4.model.News
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class FragmentList : MvpAppCompatFragment(), ClickOnNewsCallback,
    NewsListView, SwipeRefreshLayout.OnRefreshListener {


    override fun showProgessBar() {
        mProgressBar.visibility = ProgressBar.VISIBLE
    }

    override fun dismissProgressBar() {
        mProgressBar.visibility = ProgressBar.INVISIBLE
    }

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
    lateinit var presenter: NewsListPresenter
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mRecyclerView: RecyclerView
    lateinit var mProgressBar: ProgressBar

    @ProvidePresenter
    fun initPresenter(): NewsListPresenter {
        return App.component.getNewsListPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = fragment_list.findViewById(R.id.listRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mProgressBar = fragment_list.findViewById(R.id.progressBar)
        mSwipeRefreshLayout = fragment_list.findViewById(R.id.fragment_list)
        if (isFavorite!!)
            mSwipeRefreshLayout.isEnabled = false
        mSwipeRefreshLayout.setOnRefreshListener(this)
        presenter.subscribeToNetworkChanges()
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        mAdapter =
            NewsAdapter(clickOnNewsCallback = this as ClickOnNewsCallback)
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        if (!isFavorite!!) {
            presenter.subscribeToNetworkChanges()
            presenter.getNews()
        } else
            presenter.getFavoriteNews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
    }

    override fun onRefresh() {
        presenter.updateNews(isFavorite!!)
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

    override fun onItemClicked(news: News) {
        presenter.openSingleNews(news)
    }

    override fun showNetworkAlertDialog() {
        mProgressBar.visibility = ProgressBar.INVISIBLE
        val dialog = AlertDialog.Builder(context!!)
            .setTitle("Подключение к сети отсутствует")
            .setMessage("Для работы программы необходимо подключение к  сети")
            .setCancelable(false)
            .setPositiveButton("Ok", null)
        dialog.create().show()
    }

    override fun showNews(news: ArrayList<NewsContainer>) {
        mAdapter.setNews(news)
        mAdapter.notifyDataSetChanged()
    }

    override fun onInternetStateChanged(connected: Boolean) {
        mSwipeRefreshLayout.isEnabled = isFavorite!! && connected
        if (connected) {
            Snackbar.make(mSwipeRefreshLayout, "есть", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(mSwipeRefreshLayout, "нету", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showSingleNews(news: News) {
        val intent = Intent(context, NewsDetailDetailActivity::class.java).apply {
            putExtra(NewsDetailDetailActivity.ARG_ID, news.newsId)
            putExtra(NewsDetailDetailActivity.ARG_Theme, news.theme)
            putExtra(NewsDetailDetailActivity.ARG_DATE, news.date.timeInMilliseconds)
        }
        ContextCompat.startActivity(context!!, intent, null)
    }

    override fun updateListNews() {
        mAdapter.notifyDataSetChanged()
    }
}

interface ClickOnNewsCallback {
    fun onItemClicked(newsId: News)
}




