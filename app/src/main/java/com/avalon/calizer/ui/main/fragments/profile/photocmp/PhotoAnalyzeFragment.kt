package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.avalon.calizer.ui.tutorial.TutorialFragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class PhotoAnalyzeFragment : Fragment() {
    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = PhotoAnalyzeFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }

    }
    private lateinit var binding: FragmentPhotoAnalyzeBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel


    private lateinit var data: List<PhotoAnalyzeData>

   // private val args: PhotoAnalyzeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater, container, false)
       // data = args.photoAnalyze.toList()

        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }


    private fun getWidthAndHeightQuality(image: Bitmap?): Boolean? {
        return image?.let { data -> data.width >= 1080 && data.height >= 1080 }
    }


}