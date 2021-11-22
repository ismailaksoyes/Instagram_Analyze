package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.PointF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData

import com.avalon.calizer.databinding.FragmentPhotoAnalyzeLoadingBinding
import com.avalon.calizer.ui.base.BaseFragment

import kotlinx.coroutines.delay


class PhotoAnalyzeLoadingFragment : BaseFragment<FragmentPhotoAnalyzeLoadingBinding>(FragmentPhotoAnalyzeLoadingBinding::inflate) {
    private val args: PhotoAnalyzeLoadingFragmentArgs by navArgs()

    private lateinit var analyzeData: List<PhotoAnalyzeData>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyzeData = args.photoNotAnalyzeData.toList()
        val photoAnalyzeData = ArrayList<PhotoAnalyzeData>()

        lifecycleScope.launchWhenStarted {
            //Work like a charm
            delay(2000L)
            loadingNextAnim()
        }


    }

    private fun loadingNextAnim() {

        val action =
            PhotoAnalyzeLoadingFragmentDirections.actionPhotoAnalyzeLoadingFragmentToPhotoPagerFragment(
               analyzeData.toTypedArray()
            )
        findNavController().navigate(action)


    }


}