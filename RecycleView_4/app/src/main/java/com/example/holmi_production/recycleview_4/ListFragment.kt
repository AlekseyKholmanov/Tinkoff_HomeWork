package com.example.holmi_production.recycleview_4

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.db.Network.NewsObject
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.ArrayList


class ListFragment : Fragment() {


    interface ClickOnNewsCallback {
        fun onItemClicked(v: View, news: News)
    }

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
        clickOnNewsCallback = requireActivity() as ClickOnNewsCallback
    }

    override fun onDetach() {
        Log.d("TAG1", "detach")
        super.onDetach()
        compositeDisposable.dispose()
        clickOnNewsCallback = null
    }

    private lateinit var newsRepository: NewsRepository
    private var clickOnNewsCallback: ClickOnNewsCallback? = null
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
        newsRepository = NewsRepository(activity!!.applicationContext)
        mAdapter = NewsAdapter(clickOnNewsCallback = clickOnNewsCallback)
        if (!isNetworkConnection()) {
            Log.d("qwerty1", "internetama")
        } else {
            setNewsToAdapter()
        }
        listRecyclerView.adapter = mAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        super.onActivityCreated(bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        isFavorite = arguments?.getBoolean(ARG_FAVORITE)
        super.onCreate(savedInstanceState)
    }

    private fun setNewsToAdapter() {
        compositeDisposable.add(loadNewsFromNetwork()
            .map { t ->
                Log.d("qwerty1", t.news.size.toString())
                DateUtils().reformateItem(t.news)
            }
            .subscribe { it ->
                mAdapter.setNews(it)
                mAdapter.notifyDataSetChanged()
            }
        )
    }

    private fun isNetworkConnection(): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
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


