package com.avalon.calizer.ui.main.fragments.profile

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var barChart: BarChart
    private lateinit var chartList: List<PieData>

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

    fun findDistance(x1:Float,y1:Float,x2:Float,y2:Float):Float = sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))

    private fun setBarChart(){
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f,5f))
        entries.add(BarEntry(2f,9f))
        entries.add(BarEntry(3f,4f))

        val barDataSet = BarDataSet(entries,"Test")

        val barDataSetColors = ArrayList<Int>()
        barDataSetColors.add(Color.BLACK)
        barDataSetColors.add(Color.GRAY)
        barDataSetColors.add(Color.BLUE)
        barDataSetColors.add(Color.RED)
        barDataSetColors.add(Color.YELLOW)

        barDataSet.colors = barDataSetColors
        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(1f,9f))
        entries2.add(BarEntry(2f,5f))
        entries2.add(BarEntry(3f,2f))

        val barDataSet2 = BarDataSet(entries2,"Test")

        val allDataSet = ArrayList<BarDataSet>()
        allDataSet.add(barDataSet)
        allDataSet.add(barDataSet2)

        val data = BarData(allDataSet as List<IBarDataSet>?)
        val groupSpace = 0.1f
        val barSpace = 0f
        val barWidth = 0.45f
        data.barWidth = barWidth
        barChart.data = data
        barChart.xAxis.axisMinimum= 0f
        barChart.xAxis.axisMaximum = 3f
        barChart.groupBars(0f,groupSpace,barSpace)
        barChart.setTouchEnabled(false)
        barChart.isDoubleTapToZoomEnabled = false
        barChart.setDrawBorders(false)
        barChart.legend.isEnabled = false

        barChart.setDrawGridBackground(false)
        barChart.description.isEnabled = false

        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisLeft.setDrawLabels(false)
        barChart.axisLeft.setDrawAxisLine(false)

        barChart.axisRight.setDrawGridLines(false)
        barChart.axisRight.setDrawLabels(false)
        barChart.axisRight.setDrawAxisLine(false)

        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.setDrawLabels(false)
        barChart.xAxis.setDrawAxisLine(false)

        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setScaleEnabled(false)
        barChart.animateY(5000)
        barChart.invalidate()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val imageUrl: String = "https://thispersondoesnotexist.com/image"
        // TODO: Use the ViewModel
        binding.ivProfilePageIm.loadPPUrl(imageUrl)
        barChart = binding.chartPie
        setBarChart()
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    runImageFaceDetector(resource)

                }
            })

        binding.ivPpAnalyze.loadPPUrl(imageUrl)



    }

}


