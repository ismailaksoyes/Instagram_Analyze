package com.avalon.calizer.data.local.profile.photoanalyze

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class PhotoAnalyzeData(
    val id:Int? = null,
    val image: Bitmap? = null,
    val isResComp:Boolean? = false,
    val poseData: PoseData? = null

):Parcelable

