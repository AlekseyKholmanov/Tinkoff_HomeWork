package com.example.holmi_production.recycleview_4

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.holmi_production.recycleview_4.Adapters.NewsAdapter
import com.example.holmi_production.recycleview_4.Adapters.ViewPagerAdapter


class MainActivity : AppCompatActivity(),NewsAdapter.mClickListener {

    companion object {
        private const val ARG_IS_FAVORITE= "isFavorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager:ViewPager = findViewById(R.id.pager)
        setupViewPager(viewPager)

        val tabLayout:TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun createFavoriteFragment():ListFragment{
        val favoriteFragment = ListFragment()
        val bundle = Bundle()
        bundle.putBoolean(ARG_IS_FAVORITE, true)
        favoriteFragment.arguments = bundle
        return favoriteFragment
    }

    private fun setupViewPager(viewPager:ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val favoriteFragment = createFavoriteFragment()
        val lastPageName = resources.getString(R.string.lastPageName)
        val favPageName = resources.getString(R.string.favoritePageName)

        adapter.addFragment(ListFragment(), lastPageName)
        adapter.addFragment(favoriteFragment, favPageName)
        viewPager.adapter = adapter
    }
    override fun mClick(v: View, position: Int) {
        val theme = v.findViewById<TextView>(R.id.theme)
        val content = v.findViewById<TextView>(R.id.content)
        var date = ""
        var isFavorite = false
        val intent = Intent(v.context, ActivityItem::class.java).apply {
            putExtra("theme", theme.text)
            putExtra("content", content.text)
            putExtra("date", date)
            putExtra("isFavorite", isFavorite)
        }
        ContextCompat.startActivity(v.context, intent, null)
        Log.d("RecyclerView", "CLICK!")
    }
}
