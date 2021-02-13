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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import kotlin.math.abs


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var pieChart: PieChart
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

                val leftEarPosition = leftEar.position.x
                val rightEarPosition = rightEar.position.y
                val leftEyePosition = leftEye.position.x
                val rightEyePosition = leftEye.position.y
                val earWeight = leftEarPosition - rightEarPosition
                val eyesWeight = leftEyePosition - rightEyePosition
                val gozkulakarasi = (earWeight - eyesWeight) / 2
                var oranH: Float = (gozkulakarasi * 5) / earWeight
                val oran = abs(oranH)


                Log.d("Response", "sol kulak ${leftEarPosition}")
                Log.d("Response", "kulaklar arasi mesafe ${earWeight}")
                Log.d("Response", "sag kulak ${rightEarPosition}")
                Log.d("Response", "face angle x ${face.headEulerAngleX}")
                Log.d("Response", "face angle y ${face.headEulerAngleY}")
                Log.d("Response", "face angle z ${face.headEulerAngleZ}")
                Log.d("Response", "oran ${oran}")
                binding.oranTest.text = oran.toString()
                Log.d("Response", "${face.smilingProbability}")
            }

        }
            .addOnFailureListener { e ->
                Log.d("Response", "${e.message}")

            }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val imageUrl: String = "https://i4.hurimg.com/i/hurriyet/75/750x422/5d78dd5245d2a023a0d3d9d7.jpg"
        // TODO: Use the ViewModel
        binding.ivProfilePageIm.loadPPUrl(imageUrl)
        pieChart = binding.chartPie

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    runImageFaceDetector(resource)

                }
            })

        binding.drawTest.loadPPUrl(imageUrl)
        val pieEntries: ArrayList<PieEntry> = ArrayList()

        val typeAmountMap = HashMap<String, Int>()

        typeAmountMap.put("Kaybedilen", 200)
        typeAmountMap.put("Kazanilan", 230)
        typeAmountMap.put("Engelleyenler", 100)
        for (type in typeAmountMap.keys) {
            typeAmountMap[type]?.toFloat()?.let { PieEntry(it, type) }?.let { pieEntries.add(it) }
        }
        val pieDataSet = PieDataSet(pieEntries, "label");
        val colors = ArrayList<Int>();
        colors.add(Color.parseColor("#2ecc71"))
        colors.add(Color.parseColor("#e74c3c"))
        colors.add(Color.parseColor("#f39c12"))

        pieDataSet.valueTextSize = 9f
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet);
        pieData.setDrawValues(true);

        pieChart.data = pieData;
        pieChart.invalidate();


    }

}