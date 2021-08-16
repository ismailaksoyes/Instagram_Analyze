package com.avalon.calizer.data.local.profile.photoanalyze

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatePoseData(
    val sitting: Boolean?,
    val leftHandUp: Boolean?,
    val rightHandUp: Boolean?,
    val leftArmUp: Boolean?,
    val rightArmUp: Boolean?,
    val leftKneeUp: Boolean?,
    val rightKneeUp: Boolean?,
) : Parcelable {
    fun getData() = listOf(
        sitting,
        leftHandUp,
        rightHandUp,
        leftArmUp,
        rightArmUp,
        leftKneeUp,
        rightKneeUp
    )

}