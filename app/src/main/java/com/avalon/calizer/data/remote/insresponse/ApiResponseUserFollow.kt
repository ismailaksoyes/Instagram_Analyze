package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseUserFollow(
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
    val users: List<User>
)