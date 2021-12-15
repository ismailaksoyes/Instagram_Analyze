package com.avalon.calizer.data.remote.insrequest

import com.google.gson.annotations.SerializedName

data class ApiTranslation(
    @SerializedName("lang_code")
    val lang_code:String = "en"
)
