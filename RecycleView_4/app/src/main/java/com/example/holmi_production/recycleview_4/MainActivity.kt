package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.holmi_production.recycleview_4.Adapters.ViewPagerAdapter


class MainActivity : AppCompatActivity() {
    companion object {
        private const val ARG_IS_FAVORITE= "isFavorite"
    }
    val lastPageName = resources.getString(R.string.lastPageName)
    val favPageName = resources.getString(R.string.favoritePageName)

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
        adapter.addFragment(ListFragment(), lastPageName)
        adapter.addFragment(favoriteFragment, favPageName)
        viewPager.adapter = adapter
    }
}
