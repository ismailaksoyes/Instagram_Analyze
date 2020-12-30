package com.avalon.avalon.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.avalon.avalon.data.local.FollowersData

@Keep
data class ApiResponseUserFollowers(
    @SerializedName("big_list")
    val bigList: Boolean,
    @SerializedName("global_blacklist_sample")
    val globalBlacklistSample: Any,
    @SerializedName("next_max_id")
    val nextMaxId: String,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("sections")
    val sections: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("users")
    val users: List<FollowersData>
)