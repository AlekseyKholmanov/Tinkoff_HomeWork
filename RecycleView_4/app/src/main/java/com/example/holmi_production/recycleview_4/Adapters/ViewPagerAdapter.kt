package com.example.holmi_production.recycleview_4.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.holmi_production.recycleview_4.ListFragment

class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): ListFragment {
        return ListFragment.newInstance(position!=0)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {

        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)

    }
}
