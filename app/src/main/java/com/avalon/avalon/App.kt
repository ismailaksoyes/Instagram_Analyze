package com.avalon.avalon

import android.app.Application
import com.avalon.avalon.utils.MySharedPreferences

val PREFERENCES: MySharedPreferences by lazy { App.prefs!! }

class App : Application() {
    companion object{
        var prefs: MySharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences(applicationContext)
    }

}