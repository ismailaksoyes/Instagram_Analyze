package com.avalon.calizer.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide



fun  ImageView.loadPPUrl(url:String?){
    Glide.with(context)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageView.clearRecycled(){
    Glide.with(context)
        .clear(this)
}

fun getBitmap(context: Context,url: String?):Bitmap{
   return Glide.with(context)
        .asBitmap()
        .load(url)
        .submit()
        .get()
}


@BindingAdapter("app:glideCircleUrl")
fun withGlide(imageView: ImageView,url: String?){
    if(!url.isNullOrEmpty()){
        imageView.loadPPUrl(url)
    }

}