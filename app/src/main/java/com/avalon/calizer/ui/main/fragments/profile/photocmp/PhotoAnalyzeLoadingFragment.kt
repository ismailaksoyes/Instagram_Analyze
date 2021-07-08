package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeLoadingBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions

class PhotoAnalyzeLoadingFragment : Fragment() {
    private val args: PhotoAnalyzeLoadingFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoAnalyzeLoadingBinding
    private lateinit var analyzeData: List<PhotoAnalyzeData>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeLoadingBinding.inflate(inflater,container,false)
        analyzeData = args.photoNotAnalyzeData.toList()
        return binding.root
    }

    private fun createPoseData(bitmapImage:Bitmap,poseDetector: PoseDetector):PoseData{
        poseDetector.process(InputImage.fromBitmap(bitmapImage,0)).addOnSuccessListener { result ->
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

            }

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options =
            PoseDetectorOptions.Builder().setDetectorMode(PoseDetectorOptions.SINGLE_IMAGE_MODE)
                .build()
        val poseDetector = PoseDetection.getClient(options)

        analyzeData.forEach { itAnalyzeData->
            itAnalyzeData.image?.let { bitmapImage->



            }?: kotlin.run {

            }

        }

    }

    private fun getXorYCoordinates(poseLandmark: PoseLandmark?) =
        poseLandmark?.let { pose -> Pair(pose.position.x, pose.position.y) }


}