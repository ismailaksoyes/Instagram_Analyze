package com.avalon.calizer.ui.main.fragments.profile

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.utils.MySharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


@AndroidEntryPoint
class ProfileFragment : Fragment() {


    @Inject
    lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var radarChart: RadarChart
    private lateinit var chartList: List<PieData>

    @Inject
    lateinit var prefs: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun runImageFaceDetector(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()
        val detector = FaceDetection.getClient(options)
        val result = detector.process(image).addOnSuccessListener { faces ->
            val peopleSize = faces.size
            Log.d("Response", "" + peopleSize)
            for (face in faces) {
                Log.d("Response", "$face")
                val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
                val rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR)
                val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)
                val mountBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)
                val mountLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT)
                val mountRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT)
                val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
                val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
                val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)
                val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)

                val leftEarPosition = leftEar.position
                val rightEarPosition = rightEar.position
                val leftEyePosition = leftEye.position
                val rightEyePosition = rightEye.position
                val earWeight = findDistance(
                    x1 = leftEarPosition.x,
                    y1 = leftEarPosition.y,
                    x2 = rightEarPosition.x,
                    y2 = rightEarPosition.y
                )
                val eyesWeight = findDistance(
                    x1 = leftEyePosition.x,
                    y1 = leftEyePosition.y,
                    x2 = rightEyePosition.x,
                    y2 = rightEyePosition.y
                )
                val gozkulakarasi = (earWeight - eyesWeight) / 2
                var oranH: Float = (gozkulakarasi * 5) / earWeight
                val oran = abs(oranH)


                Log.d("Response", "sol kulak ${leftEarPosition}")
                Log.d("Response", "kulaklar arasi mesafe ${earWeight}")
                Log.d("Response", "sag kulak ${rightEarPosition}")
                Log.d("Response", "oran ${oran}")
                binding.tvFaceOdds.text = oran.toString()
                Log.d("Response", "${face.smilingProbability}")
            }

        }
            .addOnFailureListener { e ->
                Log.d("Response", "${e.message}")

            }

    }

    fun findDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float = sqrt(
        (x2 - x1).pow(2) + (y2 - y1).pow(
            2
        )
    )

    fun initData() {
        lifecycleScope.launchWhenStarted {
            viewModel.userData.collect {
                when (it) {
                    is ProfileViewModel.UserDataFlow.Empty -> {

                    }
                    is ProfileViewModel.UserDataFlow.GetUserDetails -> {

                    }


                }


            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clPhotoAnalyze.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_photoUploadFragment)
        }
        val imageUrl: String = "https://thispersondoesnotexist.com/image"

        viewModel.getUserDetails(prefs.selectedAccount)

        binding.clGoAccounts.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_destination_accounts)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userModel.collect {
                Log.d("datatest", "$it")
                binding.viewmodel = viewModel
            }
        }


//        Glide.with(this)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    runImageFaceDetector(resource)
//
//                }
//            })
    }


}


