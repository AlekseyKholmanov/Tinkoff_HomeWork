package com.example.holmi_production.recycleview_4.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.R
import com.example.holmi_production.recycleview_4.di.App
import com.example.holmi_production.recycleview_4.mvp.Presenter.ListNewsPresenter
import com.example.holmi_production.recycleview_4.mvp.view.ListNewsView
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class FragmentList : MvpAppCompatFragment(), ClickOnNewsCallback,
    ListNewsView {

    companion object {
        private const val ARG_FAVORITE = "isFavorite"
        @JvmStatic
        fun newInstance(isFavorite: Boolean): FragmentList {
            Log.d("qwerty","newInstanceFr")
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
    lateinit var listNewsPresenter: ListNewsPresenter

    @ProvidePresenter
    fun initPresenter(): ListNewsPresenter {
        Log.d("qwerty","init" + requireFragmentManager().hashCode())
        return App.mPresenterComponent.listPresenter()
    }

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
        getNews()
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("qwerty","onCreate")
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
        initPresenter()
    }

    private fun getNews() {
        if (!isFavorite!!) {

            Log.d("qwerty", "getnews")
            listNewsPresenter.getNews()
        } else {
            Log.d("qwerty", "getfav")
            listNewsPresenter.getFavoriteNews()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("qwerty", "attach")
    }

    override fun onItemClicked(newsId: Int) {
        listNewsPresenter.openSingleNews(newsId)
    }

    override fun showNetworkAlertDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNews(news: ArrayList<ListItem>) {
        mAdapter.setNews(news)
        mAdapter.notifyDataSetChanged()

        Log.d("qwerty", "show" )
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




