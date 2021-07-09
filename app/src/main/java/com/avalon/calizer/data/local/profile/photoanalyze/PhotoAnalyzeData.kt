package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class PhotoAnalyzeData(
    val id:Long? = 0L,
    val image: Bitmap? = null,
    val isResComp:Boolean? = false,
    val poseData: PoseData? = null

):Parcelable

