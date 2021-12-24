package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoPagerBinding
import com.avalon.calizer.shared.localization.LocalizationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoPagerFragment : Fragment() {
    private val args: PhotoPagerFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoPagerBinding
    private lateinit var viewPager:ViewPager2
    private lateinit var analyzeData: List<PhotoAnalyzeData>

    @Inject
    lateinit var localizationManager: LocalizationManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoPagerBinding.inflate(inflater,container,false)
        analyzeData = args.photoUriListArgs.toList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.localization = localizationManager
        createViewPagerAdapter()
        backBtn()
    }

    private fun createViewPagerAdapter(){
        val photoAnalyzePagerAdapter = PhotoAnalyzePagerAdapter(this,analyzeData)
        viewPager = binding.vpViewPagerPhotoAnalyze
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = photoAnalyzePagerAdapter
        viewPager.offscreenPageLimit = 3
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
        viewPager.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                else -> {
                    page.translationX = myOffset
                }
            }
        }
    }

    private var onTutorialPageChangeCallBack = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    private fun backBtn(){
        binding.ivBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }


}