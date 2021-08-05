package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.graphics.Bitmap
import android.graphics.PointF
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class FaceAnalyzeManager {

    private fun runImageFaceDetector(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()
        val detector = FaceDetection.getClient(options)
         detector.process(image).addOnSuccessListener { faces ->
           faceParts(faces)

        }
            .addOnFailureListener { e ->
                Log.d("Response", "${e.message}")

            }

    }

    private fun faceParts(faceList:List<Face>) {
        val peopleSize = faceList.size
        Log.d("Response", "" + peopleSize)
        for (face in faceList) {
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
                 leftEarPosition,
               rightEarPosition
            )
            val eyesWeight = findDistance(
                leftEyePosition,
                rightEyePosition
            )
            val eyesBtEar = (earWeight - eyesWeight) / 2
            val ratioH: Float = (eyesBtEar * 5) / earWeight
            val ratio = abs(ratioH)

            Log.d("Response", "sol kulak ${leftEarPosition}")
            Log.d("Response", "kulaklar arasi mesafe ${earWeight}")
            Log.d("Response", "sag kulak ${rightEarPosition}")
            Log.d("Response", "oran ${ratio}")
            Log.d("Response", "${face.smilingProbability}")
        }
    }

    private fun findDistance(a:PointF, b:PointF): Float = sqrt(
        (b.x - a.x).pow(2) + (b.y - a.y).pow(
            2
        )
    )

}