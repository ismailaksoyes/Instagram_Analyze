package com.avalon.calizer.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class MySharedPreferences @Inject constructor(var prefs:SharedPreferences) {


    //  private val prefs = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)

    var selectedAccount: Long
        get() = prefs.getLong(Constants.SELECTED_ACCOUNT, -1)
        set(value) = prefs.edit().putLong(Constants.SELECTED_ACCOUNT, value).apply()

    var allCookie: String?
        get() = prefs.getString(Constants.ALL_COOKIES, "")
        set(value) = prefs.edit().putString(Constants.ALL_COOKIES, value).apply()

    var followersUpdateDate: Long
        get() = prefs.getLong(Constants.FOLLOWERS_UPDATE_DATE, -1)
        set(value) = prefs.edit().putLong(Constants.FOLLOWERS_UPDATE_DATE, value).apply()

    var followingUpdateDate: Long
        get() = prefs.getLong(Constants.FOLLOWING_UPDATE_DATE, -1)
        set(value) = prefs.edit().putLong(Constants.FOLLOWING_UPDATE_DATE, value).apply()

    var firstLogin: Boolean
        get() = prefs.getBoolean(Constants.FIRST_LOGIN, true)
        set(value) = prefs.edit().putBoolean(Constants.FIRST_LOGIN,value).apply()

    var showIntro: Boolean
        get() = prefs.getBoolean(Constants.SHOW_INTRO, true)
        set(value) = prefs.edit().putBoolean(Constants.SHOW_INTRO,value).apply()





    fun savePrefs(key : String, data: Any){
        when(data){
            data as Boolean->prefs.edit().putBoolean(key,data).apply()
            data as Int->prefs.edit().putInt(key,data).apply()
            data as String->prefs.edit().putString(key,data).apply()
            data as Double->prefs.edit().putFloat(key,data.toFloat()).apply()
            data as Float->prefs.edit().putFloat(key,data).apply()
            data as Long->prefs.edit().putLong(key,data).apply()
        }
    }

}