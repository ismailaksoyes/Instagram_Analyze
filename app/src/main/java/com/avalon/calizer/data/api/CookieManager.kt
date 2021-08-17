package com.avalon.calizer.data.api

import android.util.Log
import com.avalon.calizer.utils.MySharedPreferences
import javax.inject.Inject

class CookieManager @Inject constructor(private val prefs:MySharedPreferences) {

    fun prefsTest(){
        Log.d("PrefsTest","${prefs.allCookie}")
    }

}