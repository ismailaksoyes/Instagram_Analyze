package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoPagerBinding


class PhotoPagerFragment : Fragment() {
    private val args:PhotoPagerFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoPagerBinding
    private lateinit var viewPager:ViewPager2
    private lateinit var analyzeData: List<PhotoAnalyzeData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoPagerBinding.inflate(inflater,container,false)
        analyzeData = args.photoAnalyzeDataArgs.toList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FRAGMENTTESTPAGER->",analyzeData[0].poseData?.getData().toString())
        createViewPagerAdapter()



    }

    private fun createViewPagerAdapter(){
        val photoAnalyzePagerAdapter = PhotoAnalyzePagerAdapter(this,analyzeData)
        viewPager = binding.vpViewPagerPhotoAnalyze
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = photoAnalyzePagerAdapter
    }

    private var onTutorialPageChangeCallBack = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }


}