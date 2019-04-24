package com.example.holmi_production.recycleview_4

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.net.ConnectivityManagerCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.holmi_production.recycleview_4.Adapters.ViewPagerAdapter
import com.example.holmi_production.recycleview_4.db.entity.News


class MainActivity : AppCompatActivity(), ListFragment.ClickOnNewsCallback {

    companion object {
        const val ARG_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.pager)
        setupViewPager(viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val lastPageName = resources.getString(R.string.lastPageName)
        val favPageName = resources.getString(R.string.favoritePageName)

        adapter.addPageTitle(lastPageName)
        adapter.addPageTitle(favPageName)
        viewPager.adapter = adapter
    }

    override fun onItemClicked(v: View, news: News) {
        Log.d("RecyclerView", "CLICK!")
        val intent = Intent(v.context, NewsActivity::class.java).apply {
            putExtra(ARG_ID, news.newsId)
        }
        ContextCompat.startActivity(v.context, intent, null)
    }

}
