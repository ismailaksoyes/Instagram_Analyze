package com.avalon.avalon.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPPUrl(url:String){
    Glide.with(context)
        .load(url)
        .circleCrop()
        .into(this)
}