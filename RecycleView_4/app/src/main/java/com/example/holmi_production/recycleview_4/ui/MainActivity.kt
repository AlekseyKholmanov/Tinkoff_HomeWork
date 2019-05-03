package com.example.holmi_production.recycleview_4.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.holmi_production.recycleview_4.R


class MainActivity : AppCompatActivity() {

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
        if (!isNetworkConnection()) {
            showAlertNetworkDialog()
        } else
            viewPager.adapter = adapter
    }

    private fun showAlertNetworkDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Подключение к сети отсутствует")
            .setMessage("Для работы программы необходимо подключение к  сети")
            .setCancelable(false)
            .setPositiveButton("Ok", null)
        dialog.create().show()
    }


    private fun isNetworkConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
