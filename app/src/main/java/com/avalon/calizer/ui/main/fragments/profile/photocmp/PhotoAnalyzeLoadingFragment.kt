package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeLoadingBinding
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

class PhotoAnalyzeLoadingFragment : Fragment() {
    private val args: PhotoAnalyzeLoadingFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoAnalyzeLoadingBinding
    private lateinit var analyzeData: List<PhotoAnalyzeData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoAnalyzeLoadingBinding.inflate(inflater, container, false)
        analyzeData = args.photoNotAnalyzeData.toList()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options =
            PoseDetectorOptions.Builder().setDetectorMode(PoseDetectorOptions.SINGLE_IMAGE_MODE)
                .build()
        val poseDetector = PoseDetection.getClient(options)
        val photoAnalyzeData = ArrayList<PhotoAnalyzeData>()
        lifecycleScope.launch {
            analyzeData.forEach { itAnalyzeData ->
                itAnalyzeData.image?.let { bitmapImage ->
                    photoAnalyzeData.add(
                        PhotoAnalyzeData(
                            image = bitmapImage,
                            poseData = createPoseData(bitmapImage, poseDetector)
                        )
                    )
                    Log.d("DATATEST->",createPoseData(bitmapImage, poseDetector)?.leftAnkle?.first.toString())

                } ?: kotlin.run {

                }

            }.also {
                lifecycleScope.launchWhenStarted {
                     loadingNextAnim(photoAnalyzeData)
                }


            }
        }


    }

    suspend fun loadingNextAnim(list: ArrayList<PhotoAnalyzeData>) {

        val action =
            PhotoAnalyzeLoadingFragmentDirections.actionPhotoAnalyzeLoadingFragmentToPhotoPagerFragment(
                list.toTypedArray()
            )
        findNavController().navigate(action)

    }

    private fun getXorYCoordinates(poseLandmark: PoseLandmark?) =
        poseLandmark?.let { pose -> Pair(pose.position.x, pose.position.y) }

    private suspend fun createPoseData(bitmapImage: Bitmap, poseDetector: PoseDetector): PoseData? {

        val result = poseDetectorProcess(bitmapImage, poseDetector)
        return result?.let { itResult ->
            PoseData(
                leftShoulder = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)),
                rightShoulder = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)),
                leftElbow = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_ELBOW)),
                rightElbow = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)),
                leftHip = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_HIP)),
                rightHip = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_HIP)),
                leftWrist = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_WRIST)),
                rightWrist = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_WRIST)),
                leftKnee = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_KNEE)),
                rightKnee = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_KNEE)),
                leftAnkle = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.LEFT_ANKLE)),
                rightAnkle = getXorYCoordinates(itResult.getPoseLandmark(PoseLandmark.RIGHT_ANKLE))

            )

        }
    }

    suspend fun poseDetectorProcess(bitmapImage: Bitmap, poseDetector: PoseDetector): Pose? {
        var poseData:Pose? = null
         val wait = poseDetector.process(InputImage.fromBitmap(bitmapImage, 0)).addOnSuccessListener { result->
            poseData = result
         }
        wait.await()
        return poseData

    }

}