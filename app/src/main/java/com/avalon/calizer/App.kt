package com.avalon.calizer

import android.app.Application
import com.avalon.calizer.utils.MySharedPreferences

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