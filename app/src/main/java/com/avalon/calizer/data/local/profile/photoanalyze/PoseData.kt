package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.PointF
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
}
