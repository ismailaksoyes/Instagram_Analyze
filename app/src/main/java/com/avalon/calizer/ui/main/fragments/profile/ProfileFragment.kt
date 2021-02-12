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
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

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
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .build()
        val detc = FirebaseVision.getInstance().getVisionFaceDetector(options)
        detc.detectInImage(image).addOnSuccessListener { faces ->
            val peopleSize = faces.size
            Log.d("Response", "" + peopleSize)

            for (face in faces) {
                Log.d("Response", "$face")

                Log.d("Response", "${face.smilingProbability}")


            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
        binding.ivProfilePageIm.loadPPUrl("https://www.harikalardiyari.com/wp-content/uploads/2018/08/Selfie-%C3%87ekmenin-P%C3%BCf-Noktalar%C4%B1.jpg")
        pieChart = binding.chartPie

        Glide.with(this)
            .asBitmap()
            .load("http://ia.tmgrup.com.tr/38e546/0/0/0/0/2048/1363?u=http://i.tmgrup.com.tr/samdan/2020/08/27/brad-pitt-kendisinden-29-yas-kucuk-sevgilisi-nicole-poturalski-ile-birlikte-guney-fransaya-gitti-1598532967538.jpg")
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    runImageFaceDetector(resource)

                }
            })


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