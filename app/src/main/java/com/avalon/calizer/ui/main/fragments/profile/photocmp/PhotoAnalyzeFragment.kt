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
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater, container, false)
        data = args.photoAnalyze.toList()
        return binding.root
    }

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

                Log.d("PoseDETECTED", result.allPoseLandmarks.toString())
            }
        }
        binding.direk.setOnClickListener {
            binding.gizlibtn.visibility = View.VISIBLE
        }
        binding.randomDraw.setOnClickListener {
            val location = IntArray(2)
            binding.randomDraw.getLocationOnScreen(location)
            Log.d("AxisY1",location[1].toString())
            drawItem(location[1].toFloat())
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

    fun drawItem(item:Float) {
        val cca = IntArray(2)
        binding.allcl.getLocationOnScreen(cca)
        Log.d("Axiscca",cca[1].toString())
        val bitmap = Bitmap.createBitmap(1024,  2080, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
       // canvas.drawColor(Color.RED)
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50F
        paint.isAntiAlias = true
        val offset = 50
        canvas.drawLine(
            512f,
            item,
            712f,
            item,
            paint
        )
        val ccc = IntArray(2)
        binding.paint.getLocationOnScreen(ccc)
        Log.d("Axis3","${ccc[0]} - ${ccc[1]} ")
        binding.paint.setImageBitmap(bitmap)


    }



    private fun getWidthAndHeightQuality(image: Bitmap?): Boolean? {
        return image?.let { data -> data.width >= 1080 && data.height >= 1080 }
    }


}