package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.annotation.SuppressLint
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.avalon.calizer.ui.main.fragments.profile.photocmp.PhotoAnalyzeViewModel
import com.avalon.calizer.utils.Utils
import com.avalon.calizer.utils.analyzeTextColor
import com.avalon.calizer.utils.isShimmerEnabled
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

    val viewModel: PhotoAnalyzeViewModel by viewModels()

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

        analyzeData?.let { itAnalyzeData->
            itAnalyzeData.uri?.let { itUri->
                faceAnalyzeManager.setFaceAnalyzeBitmap(uriToBitmap(itUri))
                poseAnalyzeManager.setBodyAnalyzeBitmap(uriToBitmap(itUri))
            }
            setResolution(itAnalyzeData)
            setCanvasData(itAnalyzeData)
            setResolutionImage(itAnalyzeData)

        }
        setFaceScore()
        setPoseScore()

    }
    private fun uriToBitmap(imagePath: Uri): Bitmap? {
        val bitmap = if (Build.VERSION.SDK_INT < 29) {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imagePath)
        } else {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver,
                    imagePath
                )
            )
        }
        return bitmap.copy(Bitmap.Config.ARGB_8888, bitmap.isMutable)
    }

    private fun setResolutionImage(analyzeData: PhotoAnalyzeData?) {
        val checkImage = if (getWidthAndHeightQuality(analyzeData?.image)) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_true)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_false)
        }
        binding.ivCheck.setImageDrawable(checkImage)
    }

    private fun setCanvasData(analyzeData: PhotoAnalyzeData) {
        Utils.ifTwoNotNull(analyzeData.uri,analyzeData.poseData){itUri,itPose->

            binding.cvCanvas.invalidate()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setPoseScore() {
        lifecycleScope.launchWhenCreated {
            poseAnalyzeManager.bodyAnalyze.collectLatest { itBodyState->
                when(itBodyState){
                    is PoseAnalyzeManager.BodyAnalyzeState.Loading->{
                        binding.tvPoseRate.isShimmerEnabled(true)
                    }
                    is PoseAnalyzeManager.BodyAnalyzeState.Success->{
                        binding.tvPoseRate.isShimmerEnabled(false)
                        binding.tvPoseRate.analyzeTextColor(itBodyState.score)
                        binding.tvPoseRate.text = "${itBodyState.score}%"
                    }
                    is PoseAnalyzeManager.BodyAnalyzeState.PoseDataSuccess->{
                        Utils.ifTwoNotNull(itBodyState.poseManagerData.poseData,itBodyState.poseManagerData.image){itPose,itImage->
                            binding.cvCanvas.setPoseData(
                                poseData = itPose,
                                bitmap = itImage
                            )

                        }

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
                        binding.tvFaceRate.isShimmerEnabled(true)
                    }
                    is FaceAnalyzeManager.FaceAnalyzeState.Success -> {
                        binding.tvFaceRate.isShimmerEnabled(false)
                        binding.tvFaceRate.analyzeTextColor(itFaceState.score)
                        binding.tvFaceRate.text = "${itFaceState.score}%"
                    }
                }

            }
        }
    }

    private fun setResolution(analyzeData: PhotoAnalyzeData) {
        analyzeData.uri?.let { itUri->
            val imageBitmap = uriToBitmap(itUri)
            imageBitmap?.let { itImage->
                val xPos = itImage.width
                val yPos = itImage.height
                "${xPos}x${yPos}".also { binding.tvResolution.text = it }
            }
        }
    }


}


