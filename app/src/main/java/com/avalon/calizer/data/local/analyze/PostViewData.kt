package com.avalon.calizer.data.local.analyze

import androidx.annotation.Keep

@Keep
data class PostViewData(
    val mediaId:Long? = -1,
    val likeCount:Long? = -1,
    val commentCount:Long? = -1,
    val imageUrl:String? = ""
)
