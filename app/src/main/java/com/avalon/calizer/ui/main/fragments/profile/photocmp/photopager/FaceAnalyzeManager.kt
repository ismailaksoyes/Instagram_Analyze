package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.graphics.Bitmap
import android.graphics.PointF
import android.util.Log
import com.avalon.calizer.data.local.profile.photoanalyze.FaceAnalyzeData
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class FaceAnalyzeManager()  {


    fun setFaceAnalyzeBitmap(bitmap: Bitmap?) {
        bitmap?.let { itBitmap ->
            runImageFaceDetector(itBitmap)
        }
    }

    private fun runImageFaceDetector(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        val detector = FaceDetection.getClient(options)
        detector.process(image).addOnSuccessListener { faces ->
            if (faces.size > 0) {
                faceParts(faces[0])
            }
        }
            .addOnFailureListener { e ->
                Log.e("", "${e.message}")

            }

    }

    private fun faceParts(face: Face) {

        val faceAnalyzeData = FaceAnalyzeData()
            faceAnalyzeData.apply {
                leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)?.position
                rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR)?.position
                noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)?.position
                mountBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)?.position
                mountLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
                mountRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position
                leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
                rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
                leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)?.position
                rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)?.position
                leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
                leftEyeBrowTopContour = face.getContour(FaceContour.LEFT_EYEBROW_TOP)?.points
                leftEyeBrowBottomContour = face.getContour(FaceContour.LEFT_EYEBROW_BOTTOM)?.points
                rightEyeContour = face.getContour(FaceContour.RIGHT_EYE)?.points
                rightEyeBrowTopContour = face.getContour(FaceContour.RIGHT_EYEBROW_TOP)?.points
                rightEyeBrowBottomContour =
                    face.getContour(FaceContour.RIGHT_EYEBROW_BOTTOM)?.points
                noseBridgeContour = face.getContour(FaceContour.NOSE_BRIDGE)?.points
                noseBottomContour = face.getContour(FaceContour.NOSE_BOTTOM)?.points
                upperLipTop = face.getContour(FaceContour.UPPER_LIP_TOP)?.points
                upperLipBottom = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points
                lowerLipTop = face.getContour(FaceContour.LOWER_LIP_TOP)?.points
                lowerLipBottom = face.getContour(FaceContour.LOWER_LIP_BOTTOM)?.points
                smilingProbability = face.smilingProbability
                faceContour = face.getContour(FaceContour.FACE)?.points
            }.also { faceAnalyzeScore(faceAnalyzeData) }



    }
    private fun faceAnalyzeScore(faceAnalyzeData: FaceAnalyzeData?){
        faceAnalyzeData?.let { itFaceData->
            val test1 = itFaceData.getHeightLeftEye()
            val test2 = itFaceData.getHeightLeftEyeBrow()
            val test3 = itFaceData.getHeightRightEye()
            val test4 = itFaceData.getHeightRightEyeBrow()
            val test5 = itFaceData.getIsSmiling()
            val test6 = itFaceData.faceContour
            val test7 = itFaceData.getFaceVerticalRatio()
            val test8 = itFaceData.getFaceHorizontalRatio()

            Log.d("faceAnalyzeData","$test1 $test2 $test3 $test4 $test5 ${test6?.size} $test7")
        }
    }

    private fun getRatio(a:Float,b:Float){

    }


}