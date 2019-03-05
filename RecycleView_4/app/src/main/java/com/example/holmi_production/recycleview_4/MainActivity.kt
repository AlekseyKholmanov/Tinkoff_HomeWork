package com.example.holmi_production.recycleview_4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar


class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private val tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager:ViewPager = findViewById(R.id.pager)
        setupViewPager(viewPager)

        val tabLayout:TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)



    }

    private fun setupViewPager(viewPager:ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SimpleFragment(), "Последние")
        adapter.addFragment(SimpleFragment(), "Избранное")
        viewPager.adapter = adapter

    }
}
