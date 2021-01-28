package com.avalon.calizer.utils

import android.content.Context

class MySharedPreferences(context: Context) {

    private val fileName = "my_prefs"

    private val prefs = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)

    var selectedAccount : Long
    get() = prefs.getLong(Constants.SELECTED_ACCOUNT,-1)
    set(value) = prefs.edit().putLong(Constants.SELECTED_ACCOUNT,value).apply()

    var allCookie: String?
    get() = prefs.getString(Constants.ALL_COOKIES,"")
    set(value) = prefs.edit().putString(Constants.ALL_COOKIES,value).apply()

    var followersUpdateDate:Long
    get() = prefs.getLong(Constants.FOLLOWERS_UPDATE_DATE,-1)
    set(value) = prefs.edit().putLong(Constants.FOLLOWERS_UPDATE_DATE,value).apply()

    var followingUpdateDate:Long
        get() = prefs.getLong(Constants.FOLLOWING_UPDATE_DATE,-1)
        set(value) = prefs.edit().putLong(Constants.FOLLOWING_UPDATE_DATE,value).apply()

    var firstLogin:Boolean
    get() = prefs.getBoolean(Constants.FIRST_LOGIN,true)
    set(value) = prefs.edit().putBoolean(Constants.FIRST_LOGIN,value).apply()



}