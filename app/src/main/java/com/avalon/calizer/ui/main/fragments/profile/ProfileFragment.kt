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

    private fun setupPieChart() {
        radarChart = binding.chartPie
        val radarEntires1 = ArrayList<RadarEntry>()
        val radarEntires2 = ArrayList<RadarEntry>()

        for(i in 1..5){
            val val1 = (Math.random() * 80) + 20
            radarEntires1.add(RadarEntry(val1.toFloat()))
            val val2 = (Math.random() * 80) + 20
            radarEntires2.add(RadarEntry(val2.toFloat()))
        }

        val barDataSetColors = ArrayList<Int>()
        barDataSetColors.add(ContextCompat.getColor(requireContext(),R.color.pieRed))
        barDataSetColors.add(ContextCompat.getColor(requireContext(),R.color.pieBlue))
      //  barDataSetColors.add(ContextCompat.getColor(requireContext(),R.color.colorGrey))



        val radarDataSet1 = RadarDataSet(radarEntires1, "")
        val radarDataSet2= RadarDataSet(radarEntires2, "")
        radarDataSet1.fillColor = Color.rgb(103, 110, 129)
        radarDataSet1.color = Color.rgb(103, 110, 129)
        radarDataSet1.setDrawFilled(true)
        radarDataSet1.setDrawHighlightIndicators(false)
        radarDataSet1.isDrawHighlightCircleEnabled = true
        radarDataSet1.lineWidth = 2f
        radarDataSet1.fillAlpha =180

        radarDataSet2.fillColor = Color.rgb(121, 162, 175)
        radarDataSet2.color = Color.rgb(121, 162, 175)
        radarDataSet2.setDrawFilled(true)
        radarDataSet2.setDrawHighlightIndicators(false)
        radarDataSet2.isDrawHighlightCircleEnabled = true
        radarDataSet2.lineWidth = 2f
        radarDataSet2.fillAlpha =180
        val radarDataArray = ArrayList<RadarDataSet>()
        radarDataArray.add(radarDataSet1)
        radarDataArray.add(radarDataSet2)

        val radarData = RadarData(radarDataArray as List<IRadarDataSet>?)
       radarData.setDrawValues(false)
        radarChart.data = radarData
        radarChart.description.isEnabled = false
        radarChart.legend.isEnabled = false
        radarChart.webLineWidth = 1f
        radarChart.setTouchEnabled(false)
        radarChart.yAxis.setDrawLabels(false)
        radarChart.invalidate()








    }

    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, 5f))
        entries.add(BarEntry(2f, 9f))
        entries.add(BarEntry(3f, 4f))

        val barDataSet = BarDataSet(entries, "Test")

        val barDataSetColors = ArrayList<Int>()
//        barDataSetColors.add(Color.BLACK)
//        barDataSetColors.add(Color.GRAY)
//        barDataSetColors.add(Color.BLUE)
//        barDataSetColors.add(Color.RED)
//        barDataSetColors.add(Color.YELLOW)


        barDataSet.setGradientColor(
            ContextCompat.getColor(requireContext(), R.color.graChartStart), ContextCompat.getColor(
                requireContext(),
                R.color.graChartEnd
            )
        )
        //  barDataSet.colors = barDataSetColors
        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(1f, 9f))
        entries2.add(BarEntry(2f, 5f))
        entries2.add(BarEntry(3f, 2f))

        val barDataSet2 = BarDataSet(entries2, "Test")

        val allDataSet = ArrayList<BarDataSet>()
        allDataSet.add(barDataSet)
        allDataSet.add(barDataSet2)

        val data = BarData(allDataSet as List<IBarDataSet>?)
        val groupSpace = 0.1f
        val barSpace = 0f
        val barWidth = 0.45f
        data.barWidth = barWidth
//        barChart.data = data
//        barChart.xAxis.axisMinimum= 0f
//        barChart.xAxis.axisMaximum = 3f
//        barChart.groupBars(0f,groupSpace,barSpace)
//        barChart.setTouchEnabled(false)
//        barChart.isDoubleTapToZoomEnabled = false
//        barChart.setDrawBorders(false)
//        barChart.legend.isEnabled = false
//
//        barChart.setDrawGridBackground(false)
//        barChart.description.isEnabled = false
//
//        barChart.axisLeft.setDrawGridLines(false)
//        barChart.axisLeft.setDrawLabels(false)
//        barChart.axisLeft.setDrawAxisLine(false)
//
//        barChart.axisRight.setDrawGridLines(false)
//        barChart.axisRight.setDrawLabels(false)
//        barChart.axisRight.setDrawAxisLine(false)
//
//        barChart.xAxis.setDrawGridLines(false)
//        barChart.xAxis.setDrawLabels(false)
//        barChart.xAxis.setDrawAxisLine(false)
//
//        barChart.setPinchZoom(false)
//        barChart.setDrawBarShadow(false)
//        barChart.setScaleEnabled(false)
//       // barChart.animateY(5000)
//        barChart.invalidate()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val imageUrl: String = "https://thispersondoesnotexist.com/image"

        viewModel.getUserDetails(prefs.selectedAccount)
        setupPieChart()

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


