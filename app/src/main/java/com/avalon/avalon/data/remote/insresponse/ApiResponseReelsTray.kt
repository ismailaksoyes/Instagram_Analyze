package com.avalon.avalon.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseReelsTray(
    @SerializedName("status")
    val status: String

)