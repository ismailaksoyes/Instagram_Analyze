package com.avalon.calizer.data.remote.insresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponseMediaDetails(
    @SerializedName("status")
    val status: String,
    @SerializedName("user_count")
    val userCount: Int,
    @SerializedName("users")
    val users: List<User>
) {
    @Keep
    data class User(
        @SerializedName("follow_friction_type")
        val followFrictionType: Int,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("is_private")
        val isPrivate: Boolean,
        @SerializedName("is_verified")
        val isVerified: Boolean,
        @SerializedName("latest_reel_media")
        val latestReelMedia: Int,
        @SerializedName("pk")
        val pk: Long,
        @SerializedName("profile_pic_id")
        val profilePicId: String,
        @SerializedName("profile_pic_url")
        val profilePicUrl: String,
        @SerializedName("username")
        val username: String
    )
}