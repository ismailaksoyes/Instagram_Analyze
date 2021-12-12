package com.avalon.calizer.shared.localization

import com.avalon.calizer.shared.model.LocalizationType
import javax.inject.Inject

class LocalizationManager @Inject constructor() {

   private var lastNewLocalization:HashMap<String,String> = HashMap()

    private  fun getLocalizationValue(key:String):String{
        val value = lastNewLocalization[key]
        return value?:key
    }

    fun setLocalization(localization:HashMap<String,String>){
       lastNewLocalization = localization
    }

    fun localization(key:String){
        getLocalizationValue(key)
    }






}