package com.example.holmi_production.recycleview_4

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
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.MvpView.NewsListView
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.Presenter.NewsListPresenterImpl
import com.example.holmi_production.recycleview_4.db.Network.NewsObject
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class ListFragment : MvpAppCompatFragment(), ClickOnNewsCallback, NewsListView {
    override fun onItemClicked(newsId: Int) {
        Log.d("qwerty1","clicked")
        newsListPresenter.openSingleNews(newsId)
    }

    override fun showNetworkAlertDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNews(news: List<News>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSingleNews(newsId: Int) {
        Log.d("RecyclerView", "CLICK!")
        val intent = Intent(context, NewsActivity::class.java).apply {
            putExtra(MainActivity.ARG_ID, newsId)
        }
        ContextCompat.startActivity(context!!, intent, null)
    }

    override fun updateListNews() {
        mAdapter.notifyDataSetChanged()
    }

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenterImpl

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("TAG1", "attach")

    }

    override fun onDetach() {
        Log.d("TAG1", "detach")
        super.onDetach()
        compositeDisposable.dispose()
    }

    private lateinit var newsRepository: NewsRepository
    private lateinit var mAdapter: NewsAdapter
    private val compositeDisposable = CompositeDisposable()
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
        newsRepository = NewsRepository(activity!!.applicationContext)
        mAdapter = NewsAdapter(clickOnNewsCallback = this as ClickOnNewsCallback)
        setNewsToAdapter()
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
        super.onCreate(savedInstanceState)
    }

    private fun setNewsToAdapter() {
        if (!isFavorite!!)
            compositeDisposable.add(loadNewsFromNetwork()
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    DateUtils().reformateItem(t.news)
                }
                .subscribe { it ->
                    mAdapter.setNews(it)
                }
            )
        else {
            compositeDisposable.add(newsRepository.getAllFavoriteNews()
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    DateUtils().reformateItem(t)
                }
                .subscribe { it ->
                    Log.d("Qwerty", "size ${it.size}")
                    mAdapter.setNews(it)
                }
            )
        }
    }


    private fun loadNewsFromNetwork(): Single<NewsObject> {
        return newsRepository.getNewsFromNetwork()
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadNews(): Flowable<ArrayList<ListItem>> {
        val action: Flowable<List<News>> = if (!isFavorite!!)
            newsRepository.getAllNews()
        else {
            newsRepository.getAllFavoriteNews()
        }
        return action.map { DateUtils().reformateItem(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}
interface ClickOnNewsCallback {
    fun onItemClicked(newsId: Int)
}




