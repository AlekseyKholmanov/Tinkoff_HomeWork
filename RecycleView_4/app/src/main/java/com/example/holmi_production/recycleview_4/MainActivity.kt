package com.example.holmi_production.recycleview_4

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.holmi_production.recycleview_4.Adapters.ViewPagerAdapter


class MainActivity : AppCompatActivity() {

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
        bundle.putBoolean("isFavorite", true)
        favoriteFragment.arguments = bundle
        return favoriteFragment
    }

    private fun setupViewPager(viewPager:ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val favoriteFragment = createFavoriteFragment()
        adapter.addFragment(ListFragment(), "Последние")
        adapter.addFragment(favoriteFragment, "Избранное")
        viewPager.adapter = adapter
    }
}
