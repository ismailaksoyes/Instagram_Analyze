package com.avalon.calizer.data.local.profile.photoanalyze

data class PoseData(
    val leftShoulder:Pair<Float,Float>?,
    val rightShoulder:Pair<Float,Float>?,
    val leftElbow:Pair<Float,Float>?,
    val rightElbow:Pair<Float,Float>?,
    val leftHip:Pair<Float,Float>?,
    val rightHip : Pair<Float,Float>?,
    val leftWrist:Pair<Float,Float>?,
    val rightWrist:Pair<Float,Float>?,
    val leftKnee:Pair<Float,Float>?,
    val rightKnee:Pair<Float,Float>?,
    val leftAnkle:Pair<Float,Float>?,
    val rightAnkle:Pair<Float,Float>?


){
    fun getData() = listOf(Pair(leftShoulder?.first,leftShoulder?.second),
        Pair(rightShoulder?.first,rightShoulder?.second),
        Pair(leftElbow?.first,leftElbow?.second),
        Pair(rightElbow?.first,rightElbow?.second),
        Pair(leftHip?.first,leftHip?.second),
        Pair(rightHip?.first,rightHip?.second),
        Pair(leftWrist?.first,leftWrist?.second),
        Pair(rightWrist?.first,rightWrist?.second),
        Pair(leftKnee?.first,leftKnee?.second),
        Pair(rightKnee?.first,rightKnee?.second),
        Pair(leftAnkle?.first,leftAnkle?.second),
        Pair(rightAnkle?.first,rightAnkle?.second))
}
