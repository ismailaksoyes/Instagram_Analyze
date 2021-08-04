package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
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
import com.avalon.calizer.utils.Utils
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull
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

        //getAngleSit(analyzeData?.poseData)
        analyzeData?.poseData?.let { itPoseData->
            val sitting= getSitting(itPoseData.leftShoulder,itPoseData.leftHip,itPoseData.leftKnee)
            val leftHandUp = getLeftHandUp(itPoseData.leftShoulder,itPoseData.leftElbow,itPoseData.leftWrist)
            val rightHandUp = getRightHandUp(itPoseData.rightShoulder,itPoseData.rightElbow,itPoseData.rightWrist)
            val leftArmUp = getLeftArmUp(itPoseData.leftHip,itPoseData.leftShoulder,itPoseData.leftElbow)
            val rightArmUp = getRightArmUp(itPoseData.rightHip,itPoseData.rightShoulder,itPoseData.rightElbow)
            val leftKneeUp = getLeftKneeUp(itPoseData.leftHip,itPoseData.leftKnee,itPoseData.leftAnkle)
            val rightKneeUp = getRightKneeUp(itPoseData.rightHip,itPoseData.rightKnee,itPoseData.rightAnkle)
            val calculateData = CalculateData(sitting, leftHandUp, rightHandUp, leftArmUp, rightArmUp, leftKneeUp, rightKneeUp)
            binding.tvPoseRate.text = calculatePoint(calculateData).toString()
            binding.sitTextTest.text = "$sitting $leftHandUp $rightHandUp $leftArmUp $rightArmUp $leftKneeUp $rightKneeUp"
        }

    }

    private fun lengthSquare(p1: PointF, p2: PointF): Float {
        val xDiff = p1.x - p2.x
        val yDiff = p1.y - p2.y
        return xDiff * xDiff + yDiff * yDiff
    }

    private fun calculateAngle(
        pointData: PointData
    ): TriangleData {
        val a = PointF(pointData.point1.x, pointData.point1.y)
        val b = PointF(pointData.point2.x, pointData.point2.y)
        val c = PointF(pointData.point3.x, pointData.point3.y)

        val aLength = lengthSquare(b, c)
        val bLength = lengthSquare(a, c)
        val cLength = lengthSquare(a, b)

        val aSides = sqrt(aLength)
        val bSides = sqrt(bLength)
        val cSides = sqrt(cLength)

        var alpha = acos((bLength + cLength - aLength) / (2 * bSides * cSides))
        var betta = acos((aLength + cLength - bLength) / (2 * aSides * cSides))
        var gamma = acos((aLength + bLength - cLength) / (2 * aSides * bSides))

        alpha = (alpha * 180 / PI).toFloat()
        betta = (betta * 180 / PI).toFloat()
        gamma = (gamma * 180 / PI).toFloat()
        Log.d("ANGLE", "$alpha $betta $gamma")
        return TriangleData(alpha, betta, gamma)
    }

    private fun getSitting(point1: PointF?, point2: PointF?, point3: PointF?):Boolean? {
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=120 }
    }
    private fun getLeftHandUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=90 }
    }
    private fun getRightHandUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=90 }
    }
    private fun getLeftArmUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=45 }
    }
    private fun getRightArmUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=45 }
    }
    private fun getLeftKneeUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=160 }
    }
    private fun getRightKneeUp(point1: PointF?, point2: PointF?, point3: PointF?):Boolean?{
        return getBodyAngleCalculate(point1, point2, point3)?.let { it<=160 }
    }


    private fun getBodyAngleCalculate(point1: PointF?, point2: PointF?, point3: PointF?): Float? {
        var angle:Float? = null
        Utils.ifNotNull(point1,point2,point3){p1,p2,p3 ->
            angle = calculateAngle(PointData(p1, p2, p3)).betta
        }
        return angle
    }
    private fun calculatePoint(calculateData: CalculateData):Int{
        var point = 0f

        calculateData.apply {
            sitting?.let { itSitting->
                if (itSitting){
                    leftHandUp?.let { itLeftHandUp->
                        rightHandUp?.let { itRightHandUp->
                            if((itLeftHandUp&&!itRightHandUp)||(!itLeftHandUp&&itRightHandUp)){
                                point += 100/7
                            }
                        }
                    }
                    leftArmUp?.let { itLeftArmUp->
                        rightArmUp?.let { itRightArmUp->
                            if((itLeftArmUp&&!itRightArmUp)||(!itLeftArmUp&&itRightArmUp)){
                                point += 100/7
                            }
                        }
                    }
                    leftKneeUp?.let { itLeftKneeUp->
                        rightKneeUp?.let { itRightKneeUp->
                            if((itLeftKneeUp&&!itRightKneeUp)||(!itLeftKneeUp&&itRightKneeUp)){
                                point += 100/7
                            }
                        }
                    }


                }else{
                    leftHandUp?.let { itLeftHandUp->
                        rightHandUp?.let { itRightHandUp->
                            if((itLeftHandUp&&!itRightHandUp)||(itLeftHandUp&&!itRightHandUp)){
                                point += 100/7
                            }
                        }
                    }
                    leftArmUp?.let { itLeftArmUp->
                        rightArmUp?.let { itRightArmUp->
                            if((!itLeftArmUp&&!itRightArmUp)||(!itLeftArmUp&&!itRightArmUp)){
                                point += 100/7
                            }
                        }
                    }
                    leftKneeUp?.let { itLeftKneeUp->
                        rightKneeUp?.let { itRightKneeUp->
                            if((itLeftKneeUp&&!itRightKneeUp)||(!itLeftKneeUp&&itRightKneeUp)){
                                point += 100/7
                            }
                        }
                    }
                }
            }
        }
        return point.toInt()
    }

    data class CalculateData(
        val sitting : Boolean?,
        val leftHandUp : Boolean?,
        val rightHandUp : Boolean?,
        val leftArmUp : Boolean?,
        val rightArmUp : Boolean?,
        val leftKneeUp : Boolean?,
        val rightKneeUp : Boolean?,
    )


    data class TriangleData(
        val alpha: Float?,
        val betta: Float?,
        val gamma: Float?

    )


    data class PointData(
        val point1: PointF,
        val point2: PointF,
        val point3: PointF
    )

}

