package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.graphics.Bitmap
import android.graphics.PointF
import com.avalon.calizer.data.local.profile.photoanalyze.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class PoseAnalyzeManager @Inject constructor(private val poseDetector: PoseDetector) {

    private var _imageBit: Bitmap? = null

    var poseResult: ((Int) -> Unit?)? = null

    var poseDataResult:((PoseManagerData)->Unit?)? = null




    fun setBodyAnalyzeBitmap(bitmap: Bitmap?){
        bitmap?.let { itBitmap->
        _imageBit = itBitmap
        poseDetectorProcess(itBitmap)
        }
    }

    private fun createCalculateData(poseData: PoseData){
        val calculateData = CalculatePoseData(
            sitting = poseData.getSitting(),
            leftHandUp = poseData.getLeftHandUp(),
            rightHandUp = poseData.getRightHandUp(),
            leftArmUp = poseData.getLeftArmUp(),
            rightArmUp = poseData.getRightArmUp(),
            leftKneeUp = poseData.getLeftKneeUp(),
            rightKneeUp = poseData.getRightKneeUp()
        )

        calculateScore(calculateData)

    }

    private fun getXorYCoordinates(poseLandmark: PoseLandmark?) =
        poseLandmark?.let { pose -> PointF(pose.position.x, pose.position.y) }

    private  fun createPoseData(result: Pose?) {
        result?.let { itResult->
          val poseData =  PoseData(
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
            val poseManagerData = PoseManagerData(poseData = poseData,image = _imageBit)
            poseDataResult?.invoke(poseManagerData)
            createCalculateData(poseData)
        }?: kotlin.run {
            poseResult?.invoke(0)
        }
    }
    private fun poseDetectorProcess(bitmapImage: Bitmap){
         poseDetector.process(InputImage.fromBitmap(bitmapImage, 0)).addOnSuccessListener { result->
          createPoseData(result)
        }
    }

    private fun sampleData(): ArrayList<CalculatePoseData> {
        val samplePhotoList = ArrayList<CalculatePoseData>()
        samplePhotoList.add(CalculatePoseData(sitting=false, leftHandUp=false, rightHandUp=false, leftArmUp=true, rightArmUp=true, leftKneeUp=false, rightKneeUp=false))
        samplePhotoList.add(CalculatePoseData(sitting=true, leftHandUp=false, rightHandUp=false, leftArmUp=true, rightArmUp=true, leftKneeUp=true, rightKneeUp=true))
        samplePhotoList.add(CalculatePoseData(sitting=true, leftHandUp=true, rightHandUp=false, leftArmUp=false, rightArmUp=true, leftKneeUp=true, rightKneeUp=true))
        samplePhotoList.add(CalculatePoseData(sitting=false, leftHandUp=false, rightHandUp=true, leftArmUp=true, rightArmUp=true, leftKneeUp=true, rightKneeUp=true))
        samplePhotoList.add(CalculatePoseData(sitting=true, leftHandUp=false, rightHandUp=true, leftArmUp=false, rightArmUp=true, leftKneeUp=true, rightKneeUp=true))
        samplePhotoList.add(CalculatePoseData(sitting=false, leftHandUp=false, rightHandUp=false, leftArmUp=true, rightArmUp=true, leftKneeUp=false, rightKneeUp=false))
        samplePhotoList.add(CalculatePoseData(sitting=true, leftHandUp=true, rightHandUp=false, leftArmUp=true, rightArmUp=true, leftKneeUp=true, rightKneeUp=true))
        samplePhotoList.add(CalculatePoseData(sitting=false, leftHandUp=false, rightHandUp=true, leftArmUp=true, rightArmUp=true, leftKneeUp=false, rightKneeUp=false))
        samplePhotoList.add(CalculatePoseData(sitting=false, leftHandUp=false, rightHandUp=true, leftArmUp=true, rightArmUp=true, leftKneeUp=false, rightKneeUp=false))
        return samplePhotoList

    }

    private fun calculateScore(calculateData: CalculatePoseData) {
        val pointList = mutableListOf<Float>()
        sampleData().forEachIndexed { index, value ->
            var scoreTemp = 0f
            value.getData().forEachIndexed { indexSample, valueSample ->
                if (calculateData.getData()[indexSample] == valueSample) {
                    scoreTemp += 100/7
                }

            }
            pointList.add(index,scoreTemp)
        }
        val score = pointList.maxOrNull() ?:0f
       // _bodyAnalyze.value = BodyAnalyzeState.Success(score.toInt())
        poseResult?.invoke(score.toInt())
    }


}