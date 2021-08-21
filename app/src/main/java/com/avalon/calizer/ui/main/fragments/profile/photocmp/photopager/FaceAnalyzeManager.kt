package com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager

import android.graphics.Bitmap
import android.util.Log
import com.avalon.calizer.data.local.profile.photoanalyze.FaceAnalyzeData
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class FaceAnalyzeManager @Inject constructor(var detector: FaceDetector)  {
    private val _faceAnalyze = MutableStateFlow<FaceAnalyzeState>(FaceAnalyzeState.Loading)
    val faceAnalyze :StateFlow<FaceAnalyzeState> = _faceAnalyze

    sealed class FaceAnalyzeState{
        object Loading : FaceAnalyzeState()
        data class Success(val score:Int) : FaceAnalyzeState()
    }
    fun setFaceAnalyzeBitmap(bitmap: Bitmap?) {
        bitmap?.let { itBitmap ->
            runImageFaceDetector(itBitmap)
        }
    }

     fun runImageFaceDetector(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        detector.process(image).addOnSuccessListener { faces ->
            if (faces.size > 0) {
                faceParts(faces[0])
            }else{
                _faceAnalyze.value = FaceAnalyzeState.Success(0)
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
                rightEyeOpenProbability = face.rightEyeOpenProbability
                leftEyeOpenProbability = face.leftEyeOpenProbability
                faceContour = face.getContour(FaceContour.FACE)?.points
            }.also { faceAnalyzeScore(faceAnalyzeData) }



    }
    private fun faceAnalyzeScore(faceAnalyzeData: FaceAnalyzeData?){

        faceAnalyzeData?.let { itFaceData->
            var scoreCalc = 0f
             if(itFaceData.getFaceVerticalRatio()) scoreCalc += 100/5
            if (itFaceData.getFaceHorizontalRatio()) scoreCalc += 100/5
            if(itFaceData.getBridgeToChipRatio()) scoreCalc += 100/5
            if(itFaceData.getEyeProbabilityRatio()) scoreCalc += 100/5
            if (itFaceData.getIsSmiling()) scoreCalc += 100/5
            _faceAnalyze.value = FaceAnalyzeState.Success(scoreCalc.toInt())

        }

    }

}