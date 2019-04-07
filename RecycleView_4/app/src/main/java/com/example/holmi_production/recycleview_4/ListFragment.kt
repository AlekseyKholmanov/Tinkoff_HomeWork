package com.example.holmi_production.recycleview_4

import android.content.Context
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
import com.example.holmi_production.recycleview_4.db.NewsRepository
import com.example.holmi_production.recycleview_4.db.entity.News
import com.example.holmi_production.recycleview_4.utils.DateUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), NewsRepository.UpdateFavorite {
    override fun onUpdateData() {
        Log.d("TAG1","dataUpdate")
    }

    interface ClickOnNewsCallback {
        fun onItemClicked(v: View, news: News)
    }

    companion object {
        private const val ARG_NAME = "isFavorite"
        @JvmStatic
        fun newInstance(isFavorite: Boolean): ListFragment {
            val args = Bundle()
            args.putBoolean(ARG_NAME, isFavorite)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        clickOnNewsCallback = requireActivity() as ClickOnNewsCallback
    }

    override fun onDetach() {
        super.onDetach()
        clickOnNewsCallback = null
    }

    private lateinit var newsRepository: NewsRepository
    private var clickOnNewsCallback: ClickOnNewsCallback? = null
    private lateinit var observable: Single<ArrayList<ListItem>>
    private lateinit var fObservable: Single<ArrayList<ListItem>>
    lateinit var mAdapter: NewsAdapter

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
        newsRepository = NewsRepository(activity!!.applicationContext)
        newsRepository.setOnCallbackListener(this)
        val isFav = arguments?.getBoolean(ARG_NAME)
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        if (!isFav!!) {
            loadNews()
            observable.subscribe { it ->
                mAdapter = NewsAdapter(it, clickOnNewsCallback)
                listRecyclerView.adapter = mAdapter
            }
        } else {
            loadFavoriteNews()
            fObservable.subscribe { it ->
                mAdapter = NewsAdapter(it, clickOnNewsCallback)
                listRecyclerView.adapter = mAdapter
            }
        }
    }

    fun loadNews() {
        observable = newsRepository.getAllNews()
            .map {
                DateUtils().reformateItem(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun setAdapter(news: List<ListItem>) {
        mAdapter = NewsAdapter(news, clickOnNewsCallback)
        listRecyclerView.adapter = mAdapter
    }

    fun loadFavoriteNews() {
        fObservable = newsRepository.getAllFavoriteIds()
            .flatMap { it ->
                newsRepository.getAllFavoriteNews(it)
            }
            .map { DateUtils().reformateItem(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun onResume() {
//        super.onResume()
//        val isFav = arguments?.getBoolean(ARG_NAME)
//        if (!isFav!!)
//            loadNews()
//        else
//            loadFavoriteNews()
//    }
}


