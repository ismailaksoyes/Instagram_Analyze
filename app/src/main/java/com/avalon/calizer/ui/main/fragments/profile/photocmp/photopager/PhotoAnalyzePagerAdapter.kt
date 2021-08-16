package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData

class PhotoAnalyzePagerAdapter(fragment:Fragment, private val analyzeData:List<PhotoAnalyzeData>):FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return analyzeData.size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoAnalyzeFragment.getInstance(analyzeData[position])
    }

}