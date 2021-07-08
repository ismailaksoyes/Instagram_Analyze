package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentPhotoPagerBinding


class PhotoPagerFragment : Fragment() {

    private lateinit var binding: FragmentPhotoPagerBinding
    private lateinit var viewPager:ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoPagerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewPagerAdapter()


    }

    private fun createViewPagerAdapter(){
        val photoAnalyzePagerAdapter = PhotoAnalyzePagerAdapter(this,3)
        viewPager = binding.vpViewPagerPhotoAnalyze
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = photoAnalyzePagerAdapter
    }


}