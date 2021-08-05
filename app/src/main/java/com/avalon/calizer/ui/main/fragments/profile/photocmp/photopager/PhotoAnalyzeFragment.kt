package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.avalon.calizer.ui.main.fragments.profile.photocmp.BodyAnalyzeManager
import com.avalon.calizer.ui.main.fragments.profile.photocmp.PhotoAnalyzeViewModel
import com.avalon.calizer.utils.Utils
import com.avalon.calizer.utils.analyzeTextColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt

@AndroidEntryPoint
class PhotoAnalyzeFragment : Fragment() {
    companion object {
        private const val ARG_DATA = "ARG_DATA"

        fun getInstance(data: PhotoAnalyzeData) = PhotoAnalyzeFragment().apply {
            arguments = bundleOf(ARG_DATA to data)
        }

    }

    private lateinit var binding: FragmentPhotoAnalyzeBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

    @Inject
    lateinit var analyzeManager: BodyAnalyzeManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }


    private fun getWidthAndHeightQuality(image: Bitmap?): Boolean {
        return image?.let { data -> data.width >= 1080 && data.height >= 1080 } ?: false
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val analyzeData = requireArguments().getParcelable<PhotoAnalyzeData>(ARG_DATA)
        binding.cvCanvas.setPoseData(
            poseData = analyzeData?.poseData,
            bitmap = analyzeData?.image
        )
        binding.cvCanvas.invalidate()

        val xPos = analyzeData?.image?.width
        val yPos = analyzeData?.image?.height
        binding.tvResolution.text = "${xPos}x${yPos}"
        val checkImage = if (getWidthAndHeightQuality(analyzeData?.image)) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_true)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_false)
        }
        binding.ivCheck.setImageDrawable(checkImage)

        analyzeData?.let { itAnalyzeData ->
            val score = analyzeManager.getScore(itAnalyzeData.poseData)
            binding.tvPoseRate.analyzeTextColor(score)
            binding.tvPoseRate.text = "${score}%"

        }
    }

}


