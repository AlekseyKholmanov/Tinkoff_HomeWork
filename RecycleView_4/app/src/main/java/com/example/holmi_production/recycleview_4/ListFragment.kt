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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    interface Callbacks {
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
    private lateinit var observable: Disposable
    private lateinit var fObservable: Disposable

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
        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        if (!isFav!!)
            loadNews()
        else
            loadFavoriteNews()


//        mAdapter = NewsAdapter(list,callbacks)

    }

    fun loadNews() {
        observable = newsRepository.getAllNews()
            .map { DateUtils().reformateItem(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                setAdapter(it)
                Log.d("TAG1", "news" + it.size.toString())
            }
    }

    fun setAdapter(news: List<ListItem>) {
        mAdapter = NewsAdapter(news, callbacks)
        listRecyclerView.adapter = mAdapter
    }

    fun loadFavoriteNews() {
        fObservable = newsRepository.getAllFavoriteIds()
            .flatMap { it ->
                newsRepository.getAllFavoriteNews(it)}
            .map { DateUtils().reformateItem(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                setAdapter(it)
                Log.d("TAG1", "fav" + it.size.toString())
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        observable.dispose()
        fObservable.dispose()
    }

    override fun onResume() {
        super.onResume()
    }
}


