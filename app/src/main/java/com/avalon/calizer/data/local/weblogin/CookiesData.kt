package com.avalon.calizer.data.local.weblogin

data class CookiesData(
    var csfr:String = "",
    var dsUserID:Long = 0L,
    var igDId:String  = "",
    var rur:String  = "",
    var sessID:String  = "",
    var shbid:String  = "",
    var shbts:String  = "",
    var mid:String  = "",
    val allCookie:String  = "",
    val lastControl: Boolean = true,
    val getCookies: Boolean = false

)
