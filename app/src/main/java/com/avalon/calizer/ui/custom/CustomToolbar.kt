package com.avalon.calizer.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.avalon.calizer.databinding.CustomToolbarBinding

class CustomToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
     val binding: CustomToolbarBinding

    init {
        binding =
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
    }

    var setTitle1:String
        get() = binding.toolbarTitle.text as String
        set(value) {
            binding.toolbarTitle.text = value
        }

    val onBack = binding.ivBackBtn

    @BindingAdapter("app:textTitle")
    fun setTitle(title:String?,view: CustomToolbar){
        title?.let { itTitle->
            binding.toolbarTitle.text = itTitle
        }
    }





}