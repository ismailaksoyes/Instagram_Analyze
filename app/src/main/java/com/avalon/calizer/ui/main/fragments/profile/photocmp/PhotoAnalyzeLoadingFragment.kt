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
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeLoadingBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PhotoAnalyzeLoadingFragment : Fragment() {
    private val args: PhotoAnalyzeLoadingFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoAnalyzeLoadingBinding
    private lateinit var analyzeData: List<PhotoAnalyzeData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoAnalyzeLoadingBinding.inflate(inflater, container, false)
        analyzeData = args.photoNotAnalyzeData.toList()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoAnalyzeData = ArrayList<PhotoAnalyzeData>()

        lifecycleScope.launchWhenStarted {
            //Work like a charm
            delay(2000L)
            loadingNextAnim(photoAnalyzeData)
        }


    }

    private fun loadingNextAnim(list: ArrayList<PhotoAnalyzeData>) {

        val action =
            PhotoAnalyzeLoadingFragmentDirections.actionPhotoAnalyzeLoadingFragmentToPhotoPagerFragment(
                list.toTypedArray()
            )
        findNavController().navigate(action)


    }


}