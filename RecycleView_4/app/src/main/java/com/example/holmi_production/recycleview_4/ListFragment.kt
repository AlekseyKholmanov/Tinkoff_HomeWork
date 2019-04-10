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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.ArrayList


class ListFragment : Fragment(), NewsRepository.UpdateFavorite {
    override fun onUpdateData() {
        Log.d("TAG1", "dataUpdate")
    }

    interface ClickOnNewsCallback {
        fun onItemClicked(v: View, news: News)
    }

    companion object {
        var subject: PublishSubject<Void> = PublishSubject.create()

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

        Log.d("TAG1", "attach")
        clickOnNewsCallback = requireActivity() as ClickOnNewsCallback
    }

    override fun onDetach() {
        Log.d("TAG1", "detach")
        super.onDetach()
        clickOnNewsCallback = null
    }

    private lateinit var newsRepository: NewsRepository
    private var clickOnNewsCallback: ClickOnNewsCallback? = null
    private lateinit var observable: Disposable
    private lateinit var fObservable: Disposable
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

        listRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getNews() {
        observable = loadNews()
            .subscribe { it ->
                mAdapter = NewsAdapter(it, clickOnNewsCallback)
                listRecyclerView.adapter = mAdapter
            }
    }

    private fun getFavoriteNews() {
        fObservable = loadFavoriteNews().subscribe { it ->
            mAdapter = NewsAdapter(it, clickOnNewsCallback)
            listRecyclerView.adapter = mAdapter
        }
    }

    private fun loadNews(): Single<java.util.ArrayList<ListItem>> {
        return newsRepository.getAllNews()
            .map {
                DateUtils().reformateItem(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadFavoriteNews(): Single<ArrayList<ListItem>> {
        return newsRepository.getAllFavoriteIds()
            .flatMap { it ->
                newsRepository.getAllFavoriteNews(it)
            }
            .map { DateUtils().reformateItem(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun setNewsTOFragment() {
        val isFav = arguments?.getBoolean(ARG_NAME)
        if (!isFav!!) {
            getNews()
        } else {
            getFavoriteNews()
        }
    }

    override fun onResume() {
        super.onResume()
        //обновление и сэт данных во фрагменты
        RxBus.publish(setNewsTOFragment())
        Log.d("TAG1", "resume")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("TAG1", "destroy")
    }
}


