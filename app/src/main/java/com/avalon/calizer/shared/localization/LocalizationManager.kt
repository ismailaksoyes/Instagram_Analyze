package com.avalon.calizer.shared.localization

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
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

    @BindingAdapter("textLocalization")
    fun getLocalizationKey(view:TextView,key:String?){
        key?.let { itKey->{
            val value =  getLocalizationValue(itKey)
            view.text = value
        } }

    }

    fun localization(key:String):String{
        return getLocalizationValue(key = key)
    }






}