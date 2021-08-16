package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.PointF
import android.os.Parcelable
import com.avalon.calizer.utils.Utils
import kotlinx.parcelize.Parcelize
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt

@Parcelize
data class PoseData(
    val leftShoulder: PointF?,
    val rightShoulder: PointF?,
    val leftElbow: PointF?,
    val rightElbow: PointF?,
    val leftHip: PointF?,
    val rightHip: PointF?,
    val leftWrist: PointF?,
    val rightWrist: PointF?,
    val leftKnee: PointF?,
    val rightKnee: PointF?,
    val leftAnkle: PointF?,
    val rightAnkle: PointF?


) : Parcelable {
    fun getData() = listOf(
        leftShoulder,
        rightShoulder,
        leftElbow,
        rightElbow,
        leftHip,
        rightHip,
        leftWrist,
        rightWrist,
        leftKnee,
        rightKnee,
        leftAnkle,
        rightAnkle
    )
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
        return TriangleData(alpha, betta, gamma)
    }
    private fun getBodyAngleCalculate(point1: PointF?, point2: PointF?, point3: PointF?): Float? {
        var angle: Float? = null
        Utils.ifNotNull(point1, point2, point3) { p1, p2, p3 ->
            angle = calculateAngle(PointData(p1, p2, p3)).betta
        }
        return angle
    }
     fun getSitting(): Boolean? {
        return getBodyAngleCalculate(leftShoulder, leftHip, leftKnee)?.let { it <= 120 }
    }

     fun getLeftHandUp(): Boolean? {
        return getBodyAngleCalculate(leftShoulder, leftElbow, leftWrist)?.let { it <= 90 }
    }

     fun getRightHandUp(): Boolean? {
        return getBodyAngleCalculate(rightShoulder, rightElbow, rightWrist)?.let { it <= 90 }
    }

     fun getLeftArmUp(): Boolean? {
        return getBodyAngleCalculate(leftHip, leftShoulder, leftElbow)?.let { it <= 45 }
    }

     fun getRightArmUp(): Boolean? {
        return getBodyAngleCalculate(rightHip, rightShoulder, rightElbow)?.let { it <= 45 }
    }

     fun getLeftKneeUp(): Boolean? {
        return getBodyAngleCalculate(leftHip, leftKnee, leftAnkle)?.let { it <= 160 }
    }

     fun getRightKneeUp(): Boolean? {
        return getBodyAngleCalculate(rightHip, rightKnee, rightAnkle)?.let { it <= 160 }
    }
}
