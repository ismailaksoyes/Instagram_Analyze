package com.avalon.calizer

import android.app.Application
import com.avalon.calizer.utils.MySharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp

//val PREFERENCES: MySharedPreferences by lazy { App.prefs!! }

@HiltAndroidApp
class App : Application() {


    override fun onCreate() {
        super.onCreate()
       // prefs = MySharedPreferences(applicationContext)

    }

}