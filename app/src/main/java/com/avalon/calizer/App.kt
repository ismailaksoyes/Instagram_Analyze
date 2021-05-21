package com.avalon.calizer

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //setTheme(R.style.Theme_Calizer)

    }

}