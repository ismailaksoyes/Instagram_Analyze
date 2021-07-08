package com.avalon.calizer.ui.main.fragments.profile.photocmp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData

class PhotoAnalyzePagerAdapter(fragment:Fragment, val itemsCount: Int):FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoAnalyzeFragment.getInstance(position)
    }

}