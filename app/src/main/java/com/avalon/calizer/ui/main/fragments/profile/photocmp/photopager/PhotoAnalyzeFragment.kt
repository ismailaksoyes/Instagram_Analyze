package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.avalon.calizer.ui.main.fragments.profile.photocmp.PhotoAnalyzeViewModel
import com.avalon.calizer.utils.analyzeTextColor
import com.avalon.calizer.utils.shimmerText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

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
    lateinit var poseAnalyzeManager: PoseAnalyzeManager

    @Inject
    lateinit var faceAnalyzeManager: FaceAnalyzeManager


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

    private fun initData() {
        val analyzeData = requireArguments().getParcelable<PhotoAnalyzeData>(ARG_DATA)
        faceAnalyzeManager.setFaceAnalyzeBitmap(analyzeData?.image)
        poseAnalyzeManager.setBodyAnalyze(analyzeData?.poseData)
        setResolution(analyzeData)
        setFaceScore()
        setPoseScore()
        setCanvasData(analyzeData)
        setResolutionImage(analyzeData)


    }

    fun setResolutionImage(analyzeData: PhotoAnalyzeData?) {
        val checkImage = if (getWidthAndHeightQuality(analyzeData?.image)) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_true)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_false)
        }
        binding.ivCheck.setImageDrawable(checkImage)
    }

    fun setCanvasData(analyzeData: PhotoAnalyzeData?) {
        binding.cvCanvas.setPoseData(
            poseData = analyzeData?.poseData,
            bitmap = analyzeData?.image
        )
        binding.cvCanvas.invalidate()
    }

    private fun setPoseScore() {
        lifecycleScope.launchWhenCreated {
            poseAnalyzeManager.bodyAnalyze.collectLatest { itBodyState->
                when(itBodyState){
                    is PoseAnalyzeManager.BodyAnalyzeState.Loading->{
                        binding.tvPoseRate.shimmerText(true)
                    }
                    is PoseAnalyzeManager.BodyAnalyzeState.Success->{
                        binding.tvPoseRate.shimmerText(false)
                        binding.tvPoseRate.analyzeTextColor(itBodyState.score)
                        binding.tvPoseRate.text = "${itBodyState.score}%"
                    }
                }
            }
        }
    }

    private fun setFaceScore() {
        lifecycleScope.launchWhenCreated {
            faceAnalyzeManager.faceAnalyze.collectLatest { itFaceState ->
                when (itFaceState) {
                    is FaceAnalyzeManager.FaceAnalyzeState.Loading -> {
                        binding.tvFaceRate.shimmerText(true)
                    }
                    is FaceAnalyzeManager.FaceAnalyzeState.Success -> {
                        binding.tvFaceRate.shimmerText(false)
                        binding.tvFaceRate.analyzeTextColor(itFaceState.score)
                        binding.tvFaceRate.text = "${itFaceState.score}%"
                    }
                }

            }
        }
    }

    fun setResolution(analyzeData: PhotoAnalyzeData?) {
        analyzeData?.let { itAnalyzeData ->
            itAnalyzeData.image?.let { itImage ->
                val xPos = itImage.width
                val yPos = itImage.height
                "${xPos}x${yPos}".also { binding.tvResolution.text = it }
            }

        }
    }

}


