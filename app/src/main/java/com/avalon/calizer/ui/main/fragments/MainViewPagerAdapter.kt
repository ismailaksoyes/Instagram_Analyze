package com.avalon.calizer.ui.main.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
    private val fragmentList: ArrayList<Fragment> = ArrayList()
    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
       return fragmentList[position]
    }
    fun AddFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }


}