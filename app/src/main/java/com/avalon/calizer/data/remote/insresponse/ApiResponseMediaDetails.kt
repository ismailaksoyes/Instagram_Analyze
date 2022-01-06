package com.avalon.calizer.data.remote.insresponse


import androidx.annotation.Keep

@Keep
data class ApiResponseMediaDetails(
    val status: String,
    val user_count: Int,
    val users: List<User>
) {
    @Keep
    data class User(
        val follow_friction_type: Int,
        val full_name: String,
        val is_private: Boolean,
        val is_verified: Boolean,
        val latest_reel_media: Int,
        val pk: Long,
        val profile_pic_id: String,
        val profile_pic_url: String,
        val username: String
    )
}