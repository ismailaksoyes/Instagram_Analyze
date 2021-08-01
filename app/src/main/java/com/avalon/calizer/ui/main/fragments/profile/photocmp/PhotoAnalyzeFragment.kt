package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import com.avalon.calizer.ui.tutorial.TutorialFragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.Math.atan2
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt
import kotlin.random.Random

@AndroidEntryPoint
class PhotoAnalyzeFragment : Fragment() {
    companion object {
        private const val ARG_DATA = "ARG_DATA"

        fun getInstance(data: PhotoAnalyzeData) = PhotoAnalyzeFragment().apply {
            arguments = bundleOf(ARG_DATA to data)
        }

    }

    private lateinit var binding: FragmentPhotoAnalyzeBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

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

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val analyzeData = requireArguments().getParcelable<PhotoAnalyzeData>(ARG_DATA)
        binding.cvCanvas.setPoseData(
            poseData = analyzeData?.poseData,
            bitmap = analyzeData?.image
        )
        binding.cvCanvas.invalidate()

        val xPos = analyzeData?.image?.width
        val yPos = analyzeData?.image?.height
        binding.tvResolution.text = "${xPos}x${yPos}"
        val checkImage = if (getWidthAndHeightQuality(analyzeData?.image)) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_true)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_false)
        }
        binding.ivCheck.setImageDrawable(checkImage)

        getAngleSit(analyzeData?.poseData)

    }

    private fun lengthSquare(p1: Point, p2: Point): Int {
        val xDiff = p1.x - p2.x
        val yDiff = p1.y - p2.y
        return xDiff * xDiff + yDiff * yDiff
    }

    fun calculateAngle(
        aPoint: PointF,
        bPoint: PointF,
        cPoint: PointF
    ): TriangleData {
        val a = Point(aPoint.x.toInt(), aPoint.y.toInt())
        val b = Point(bPoint.x.toInt(), bPoint.y.toInt())
        val c = Point(cPoint.x.toInt(), cPoint.y.toInt())

        val aLength = lengthSquare(b, c)
        val bLength = lengthSquare(a, c)
        val cLength = lengthSquare(a, b)

        val aSides = sqrt(aLength.toFloat())
        val bSides = sqrt(bLength.toFloat())
        val cSides = sqrt(cLength.toFloat())

        var alpha = acos((bLength + cLength - aLength) / (2 * bSides * cSides))
        var betta = acos((aLength + cLength - bLength) / (2 * aSides * cSides))
        var gamma = acos((aLength + bLength - cLength) / (2 * aSides * bSides))

        alpha = (alpha * 180 / PI).toFloat()
        betta = (betta * 180 / PI).toFloat()
        gamma = (gamma * 180 / PI).toFloat()
        Log.d("ANGLE", "$alpha $betta $gamma")
        return TriangleData(alpha, betta, gamma)
    }

    fun getAngleSit(poseData: PoseData?) {
        poseData?.let { itPoseData ->
            val leftShoulder = itPoseData.leftShoulder
            val leftHip =itPoseData.leftHip
            val leftKnee = itPoseData.leftKnee
            if(leftShoulder!=null&&leftHip!=null&&leftKnee!=null){
             val betta=  calculateAngle(leftShoulder,leftHip,leftKnee).betta
                betta?.let {
                    val sitTest = if(it<120)"OTURUYOR" else "AYAKTA YADA BACAGI YOK"
                    Log.d("ANGLE","$sitTest")
                    binding.sitTextTest.text = sitTest

                }
            }

        }

    }
    fun getAngleLeftArm(poseData: PoseData?){
        poseData?.let { itPoseData->
            val leftShoulder = itPoseData.leftShoulder
            val leftElbow = itPoseData.leftElbow
            val leftWrist  = itPoseData.leftWrist
            if(leftElbow!=null&&leftShoulder!=null&&leftWrist!=null){
                val betta = calculateAngle(leftShoulder,leftElbow,leftWrist).betta
                betta
            }
        }

    }
    fun getBodyAngle(pointData: PointData){
        
    }

    data class TriangleData(
        val alpha: Float?,
        val betta: Float?,
        val gamma: Float?
    )
    data class PointData(
        val point1 : Float?,
        val point2 : Float?,
        val point3 : Float?
    )


}