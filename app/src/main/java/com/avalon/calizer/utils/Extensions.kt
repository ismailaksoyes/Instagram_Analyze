package com.avalon.calizer.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.avalon.calizer.data.local.FollowData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable


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

fun ArrayList<FollowData>.getItemByID(item: Long?): Int {
    return indexOf(this.first { it.dsUserID == item })
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