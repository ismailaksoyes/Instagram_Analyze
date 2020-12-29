package com.avalon.avalon.utils.Preferences

import android.content.Context

class CookieSharedPreferences(context: Context) {

    private val fileName = "cookie_prefs"

    private val prefs = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)

    var allCookie: String?
    get() = prefs.getString("allCookies","")
    set(value) = prefs.edit().putString("allCookies",value).apply()

    var followersUpdateDate:Long
    get() = prefs.getLong("followersUpdateDate",-1)
    set(value) = prefs.edit().putLong("followersUpdateDate",value).apply()


}