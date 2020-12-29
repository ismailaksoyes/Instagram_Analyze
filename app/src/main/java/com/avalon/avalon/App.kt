package com.avalon.avalon

import android.app.Application
import com.avalon.avalon.utils.Preferences.CookieSharedPreferences

val preferences:CookieSharedPreferences by lazy { App.prefs!! }

class App : Application() {
    companion object{
        var prefs:CookieSharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = CookieSharedPreferences(applicationContext)
    }

}