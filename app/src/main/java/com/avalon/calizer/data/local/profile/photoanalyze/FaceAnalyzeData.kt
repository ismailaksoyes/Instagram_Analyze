package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.PointF
import android.os.Parcelable
import com.avalon.calizer.utils.Utils
import com.bumptech.glide.util.Util
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
    var smilingProbability:Float? =null,
    var faceContour: List<PointF>? = null,
):Parcelable{
    private fun getDistance2Point(pointF: PointF?,pointF2: PointF?):Float?{
        return Utils.ifTwoNotNull(pointF,pointF2){a,b->
            sqrt(((a.x-b.x).pow(2)).toDouble()+((a.y-b.y).pow(2)).toDouble()).toFloat()
        }?:run { null }

    }

    private fun getTwoRatioCheck(n1: Float?,n2: Float?):Boolean{
        var controlRatio = false
        Utils.ifTwoNotNull(n1,n2){x,y->
            val ratio = x/y
            if (ratio in 0.25f..0.41f){
                controlRatio = true
            }
        }?: kotlin.run { controlRatio=false }
        return controlRatio
    }
    private fun getTriRatioCheck(n1:Float?,n2:Float?,n3:Float?):Boolean{
        var controlRatio = false
        Utils.ifNotNull(n1,n2,n3){x,y,z->
            val average = (x+y+y)/3
            val rot = average*0.2f
            val rotNeg = average-rot
            val rotPoz = average+rot
            if (x in rotNeg..rotPoz&&y in rotNeg..rotPoz&&z in rotNeg..rotPoz){
                controlRatio = true
            }
        }?: kotlin.run { controlRatio=false }
        return controlRatio
    }
    private fun getFiveRatioCheck(n1:Float?,n2:Float?,n3:Float?,n4:Float?,n5:Float?):Boolean{
        var controlRatio = false
        Utils.ifFiveNotNull(n1,n2,n3,n4,n5){x,y,z,e,f->
            val average = (x+y+y+e+f)/3
            val rot = average*0.2f
            val rotNeg = average-rot
            val rotPoz = average+rot
            if (x in rotNeg..rotPoz&&y in rotNeg..rotPoz&&z in rotNeg..rotPoz&&e in rotNeg..rotPoz&&f in rotNeg..rotPoz){
                controlRatio = true
            }
        }?: kotlin.run { controlRatio=false }
        return controlRatio
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
    fun getWeightRightEye():Float?{
        return getDistance2Point(rightEyeContour?.get(0),rightEyeContour?.get(8))
    }
    fun getWeightLeftEye():Float?{
        return getDistance2Point(leftEyeContour?.get(0),leftEyeContour?.get(8))
    }
    fun getWeightBetweenEyes():Float?{
        return getDistance2Point(rightEyeContour?.get(8),leftEyeContour?.get(0))
    }

    fun getNoseBridgeHeight():Float?{
        return getDistance2Point(noseBridgeContour?.get(0),noseBridgeContour?.get(1))
    }
    fun getIsSmiling():Boolean?{
        return smilingProbability?.let { itSmile-> itSmile>0.7f }
    }

    fun getHairNoseHeight():Float?{
        return getDistance2Point(faceContour?.get(0),noseBridgeContour?.get(0))
    }

    fun getNoseToJaw():Float?{
        return getDistance2Point(noseBottomContour?.get(1),faceContour?.get(18))
    }

    fun getRightEarToRightEye():Float?{
        return getDistance2Point(faceContour?.get(29),rightEyeContour?.get(0))
    }

    fun getLeftEarToLeftEye():Float?{
        return getDistance2Point(faceContour?.get(7),leftEyeContour?.get(8))
    }
    fun getBridgeToLipHeight():Float?{
        return getDistance2Point(noseBridgeContour?.get(1),upperLipBottom?.get(4))
    }
    fun getLipToChipHeight():Float?{
        return getDistance2Point(upperLipBottom?.get(4),faceContour?.get(18))
    }

    fun getFaceVerticalRatio():Boolean{
        return getTriRatioCheck(getNoseBridgeHeight(),getHairNoseHeight(),getNoseToJaw())
    }

    fun getFaceHorizontalRatio():Boolean{
        return getFiveRatioCheck(getRightEarToRightEye(),getWeightRightEye(),getWeightBetweenEyes(),getWeightLeftEye(),getLeftEarToLeftEye())
    }

    fun getBridgeToChipRatio():Boolean{
        return getTwoRatioCheck(getBridgeToLipHeight(),getLipToChipHeight())
    }






}

