package com.avalon.calizer.shared.localization

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.avalon.calizer.shared.model.LocalizationType
import com.avalon.calizer.utils.MySharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class LocalizationManager @Inject constructor(val prefs: MySharedPreferences) {


   private var lastNewLocalization:HashMap<String,String> = HashMap()


    private  fun getLocalizationValue(key:String):String{
        var value = lastNewLocalization[key]
        if (value==null){
            val localString = prefs.translation
            if (localString!=null){
                try {
                    val hash:HashMap<String,String> = HashMap()
                    val newLocalization = Gson().fromJson(localString,hash::class.java)
                    lastNewLocalization = newLocalization
                    value = lastNewLocalization[key]
                }
                catch (e: Exception){

                }
            }
        }
        return value?:key
    }

    fun setLocalization(localization:HashMap<String,String>){
       lastNewLocalization = localization
        saveLocalization(localization)
    }

    private fun saveLocalization(localization:HashMap<String,String>){
        prefs.translation = Gson().toJson(localization).toString()
    }


    fun localization(key:String):String{
        return getLocalizationValue(key = key)
    }






}