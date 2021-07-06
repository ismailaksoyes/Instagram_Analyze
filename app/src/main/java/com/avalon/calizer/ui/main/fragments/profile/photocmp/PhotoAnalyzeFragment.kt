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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
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
    private lateinit var binding: FragmentPhotoAnalyzeBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel


    private lateinit var data: List<PhotoAnalyzeData>

    private val args: PhotoAnalyzeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater, container, false)
        data = args.photoAnalyze.toList()
        return binding.root
    }

    private fun getXorYCoordinates(poseLandmark: PoseLandmark?) =
        poseLandmark?.let { pose -> Pair(pose.position.x, pose.position.y) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options =
            PoseDetectorOptions.Builder().setDetectorMode(PoseDetectorOptions.SINGLE_IMAGE_MODE)
                .build()
        val poseDetector = PoseDetection.getClient(options)
        data.forEach {
            Log.d("Aaaa", it.toString())
            Log.d("Aaaa", getWidthAndHeightQuality(it.image).toString())
        }
        val image = data[0].image?.let {
            InputImage.fromBitmap(it, 0)

        }

        image?.let {
            poseDetector.process(it).addOnSuccessListener { result ->
                if (!result.allPoseLandmarks.isNullOrEmpty()){
                    val poseData = PoseData(
                        leftShoulder = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)),
                        rightShoulder = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)),
                        leftElbow = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_ELBOW)),
                        rightElbow = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)),
                        leftHip = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_HIP)),
                        rightHip = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_HIP)),
                        leftWrist = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_WRIST)),
                        rightWrist = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_WRIST)),
                        leftKnee = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_KNEE)),
                        rightKnee = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_KNEE)),
                        leftAnkle = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.LEFT_ANKLE)),
                        rightAnkle = getXorYCoordinates(result.getPoseLandmark(PoseLandmark.RIGHT_ANKLE))

                    )



                    binding.cvCanvas.setPoseData(poseData, data[0].image)
                    binding.cvCanvas.invalidate()
                }

            }
        }

        binding.randomDraw.setOnClickListener {

            binding.cvCanvas.invalidate()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.photoAnalyzeData.collect { data ->
                when (data) {
                    is PhotoAnalyzeViewModel.PhotoState.Success -> {
                        Log.d("DataPhoto", data.toString())
                    }
                    is PhotoAnalyzeViewModel.PhotoState.Random -> {
                        Log.d("DataPhoto", data.data.toString())
                    }
                    else -> {
                        Log.d("DataPhoto", "else")
                    }
                }
            }

        }

    }


    private fun getWidthAndHeightQuality(image: Bitmap?): Boolean? {
        return image?.let { data -> data.width >= 1080 && data.height >= 1080 }
    }


}