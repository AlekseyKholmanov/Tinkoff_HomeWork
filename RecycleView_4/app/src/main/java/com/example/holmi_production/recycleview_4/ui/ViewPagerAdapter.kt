package com.example.holmi_production.recycleview_4.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.holmi_production.recycleview_4.ui.ListFragment

class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return ListFragment.newInstance(position != 0)
    }

    private val mFragmentTitleList = ArrayList<String>()

    override fun getCount(): Int {
        return mFragmentTitleList.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addPageTitle(title: String) {
        mFragmentTitleList.add(title)
    }

}
