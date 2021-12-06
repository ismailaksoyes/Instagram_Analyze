package com.avalon.calizer.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.avalon.calizer.databinding.CustomToolbarBinding

class CustomToolbar(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
     val binding: CustomToolbarBinding

    init {
        binding =
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
    }

    var setTitle:String
        get() = binding.toolbarTitle.text as String
        set(value) {
            binding.toolbarTitle.text = value
        }

    val onBack = binding.ivBackBtn


}