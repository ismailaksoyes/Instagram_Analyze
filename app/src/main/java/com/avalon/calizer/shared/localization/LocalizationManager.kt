package com.avalon.calizer.shared.localization

import com.avalon.calizer.shared.model.LocalizationType.TEST_TITLE
import javax.inject.Inject

class LocalizationManager @Inject constructor() {
   // private lateinit var lastNewLocalization:HashMap<String,String>
    val lastNewLocalization:HashMap<String,String> = HashMap<String,String>()

    private  fun getLocalizationValue(key:String):String{
        val value = lastNewLocalization[key]
        return value?:key
    }

    fun setLocalization(test:String){
        lastNewLocalization[TEST_TITLE] = test
    }

    fun localization(key:String) = getLocalizationValue(key)



}