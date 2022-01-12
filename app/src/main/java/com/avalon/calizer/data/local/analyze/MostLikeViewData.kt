package com.avalon.calizer.data.local.analyze

import androidx.annotation.Keep

@Keep
data class MostLikeViewData(
    val profileUrl:String? = null,
    val likeCount:Long? = null,
    val username:String? = null
)
