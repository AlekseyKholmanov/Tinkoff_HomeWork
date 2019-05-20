package com.example.holmi_production.recycleview_4.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.App
import com.example.holmi_production.recycleview_4.detail.NewsDetailDetailActivity
import com.example.holmi_production.recycleview_4.model.News
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class FragmentList : MvpAppCompatFragment(), ClickOnNewsCallback,
    NewsListView {
    override fun showError(error: Throwable) {
        Snackbar.make(refreshLayout, R.string.error, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading(show: Boolean) {
        refreshLayout.isRefreshing = show
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

        listRecyclerView.layoutManager = LinearLayoutManager(activity)
        refreshLayout.isEnabled = !isFavorite!!
        refreshLayout.setOnRefreshListener { presenter.getNews(true) }
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        mAdapter = NewsAdapter(clickOnNewsCallback = this as ClickOnNewsCallback)
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        if (isFavorite!!)
            presenter.getFavoriteNews()
        else {
            presenter.subscribeToNetworkChanges()
            presenter.getNews(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
    }

    override fun onItemClicked(newsId: News) {
        presenter.openSingleNews(newsId)
    }

    override fun showNews(news: ArrayList<NewsContainer>) {
        mAdapter.setNews(news)
        mAdapter.notifyDataSetChanged()
    }

    override fun onInternetStateChanged(connected: Boolean) {
        refreshLayout.isEnabled = isFavorite!! && connected
        if (connected) {
            Snackbar.make(refreshLayout, "Подкоючение восстановлено", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(refreshLayout, "Отсутствует доступ к сети", Snackbar.LENGTH_LONG).show()
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
}

interface ClickOnNewsCallback {
    fun onItemClicked(newsId: News)
}




