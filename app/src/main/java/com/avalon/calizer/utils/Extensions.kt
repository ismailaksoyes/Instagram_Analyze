package com.avalon.calizer.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.Snackbar


fun ImageView.loadPPUrl(url: String?) {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
    val shimmerDrawable = ShimmerDrawable().apply { setShimmer(shimmer) }
    Glide.with(context)
        .load(url)
        .error(shimmerDrawable)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .placeholder(shimmerDrawable)
        .into(this)
}

fun ImageView.clearRecycled() {
    Glide.with(context)
        .clear(this)
}

fun TextView.analyzeTextColor(score:Int){
    when(score){
        in 0..30 -> this.setTextColor(ContextCompat.getColor(this.context,R.color.lightRed))
        in 31..69 -> this.setTextColor(ContextCompat.getColor(this.context,R.color.lightYellow))
        in 70..100 -> this.setTextColor(ContextCompat.getColor(this.context,R.color.green))
    }

}

fun ArrayList<FollowData>.getItemByID(item: Long?): Int {
    return indexOf(this.first { it.dsUserID==item})
}

fun getBitmap(context: Context, url: String?): Bitmap {
    return Glide.with(context)
        .asBitmap()
        .load(url)
        .submit()
        .get()
}


@BindingAdapter("app:glideCircleUrl")
fun withGlide(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        imageView.loadPPUrl(url)
    } else {
        imageView.loadPPUrl(url)
    }

}

fun View.showSnackBar(
    view: View,
    msg:String,
    length:Int,
    actionMessage:CharSequence?,
    action:(View) -> Unit
){
    val snackbar = Snackbar.make(view,msg,length)
    if (actionMessage!=null){
        snackbar.setAction(actionMessage){
            action(this)
        }.show()
    }else{
        snackbar.show()
    }
}