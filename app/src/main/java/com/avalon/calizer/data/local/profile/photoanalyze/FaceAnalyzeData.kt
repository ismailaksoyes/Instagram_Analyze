package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.PointF
import android.os.Parcelable
import com.avalon.calizer.utils.Utils
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@Parcelize
data class FaceAnalyzeData(
    var leftEar :PointF? = null,
    var rightEar:PointF? = null,
    var noseBase :PointF?= null,
    var mountBottom  :PointF?= null,
    var mountLeft:PointF?= null,
    var mountRight :PointF?= null,
    var leftEye :PointF?= null,
    var rightEye :PointF?= null,
    var leftCheek :PointF?= null,
    var rightCheek:PointF?= null,
    var leftEyeContour : List<PointF>?= null,
    var leftEyeBrowTopContour : List<PointF>?= null,
    var leftEyeBrowBottomContour : List<PointF>?= null,
    var rightEyeContour : List<PointF>?= null,
    var rightEyeBrowTopContour : List<PointF>?= null,
    var rightEyeBrowBottomContour : List<PointF>?= null,
    var noseBridgeContour : List<PointF>?= null,
    var noseBottomContour : List<PointF>?= null,
    var upperLipTop:List<PointF>?= null,
    var upperLipBottom:List<PointF>?= null,
    var lowerLipTop:List<PointF>?= null,
    var lowerLipBottom:List<PointF>?= null,
    var smilingProbability:Float? =null
):Parcelable{
    private fun getDistance2Point(pointF: PointF?,pointF2: PointF?):Float?{
        return Utils.ifTwoNotNull(pointF,pointF2){a,b->
            sqrt(((a.x-b.x).pow(2)).toDouble()+((a.y-b.y).pow(2)).toDouble()).toFloat()
        }?:run { null }

    }

    fun getHeightRightEye():Float?{
        return getDistance2Point(rightEyeContour?.get(4),rightEyeContour?.get(12))
    }
    fun getHeightLeftEye():Float?{
        return getDistance2Point(leftEyeContour?.get(4),leftEyeContour?.get(12))
    }
    fun getHeightLeftEyeBrow():Float?{
        return getDistance2Point(leftEyeBrowTopContour?.get(4),leftEyeBrowBottomContour?.get(4))
    }
    fun getHeightRightEyeBrow():Float?{
        return getDistance2Point(rightEyeBrowTopContour?.get(4),rightEyeBrowBottomContour?.get(4))
    }
    fun getWeightLeftEyeBrow():Float?{
        return getDistance2Point(leftEyeBrowBottomContour?.get(0),leftEyeBrowBottomContour?.get(4))
    }

    fun getNoseBridgeHeight():Float?{
        return getDistance2Point(noseBridgeContour?.get(0),noseBridgeContour?.get(1))
    }
    fun getIsSmiling():Boolean?{
        return smilingProbability?.let { itSmile-> itSmile>0.7f }
    }





}

