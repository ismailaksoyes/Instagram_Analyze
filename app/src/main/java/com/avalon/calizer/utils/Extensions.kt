package com.avalon.calizer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.avalon.calizer.R
import com.avalon.calizer.data.local.follow.*
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
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

fun TextView.isShimmerEnabled(start: Boolean) {
    if (start) {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply { setShimmer(shimmer) }
        this.text = "00000"
        this.setTextColor(Color.TRANSPARENT)
        this.background = shimmerDrawable
    } else {
        this.background = null
    }
}

fun TextView.isTransShimmerEnabled(start: Boolean) {
    if (start) {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply { setShimmer(shimmer) }
        this.text = "\\u0020"
        this.background = shimmerDrawable
    } else {
        this.background = null
    }
}


fun ImageView.clearRecycled() {
    Glide.with(context)
        .clear(this)
}

fun TextView.analyzeTextColor(score: Int) {
    when (score) {
        in 0..30 -> this.setTextColor(ContextCompat.getColor(this.context, R.color.red_light))
        in 31..69 -> this.setTextColor(ContextCompat.getColor(this.context, R.color.yellow_light))
        in 70..100 -> this.setTextColor(ContextCompat.getColor(this.context, R.color.green))
    }

}

fun ArrayList<FollowersData>.getItemByID(item: Long?): Int {
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

fun View.showSnackBar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}

fun ApiResponseUserFollow.toFollowData(userId: Long) = users.map { itUserData ->
    FollowData(
        uniqueType = itUserData.pk.toString().plus(userId).toLong(),
        analyzeUserId = userId,
        dsUserID = itUserData.pk,
        fullName = itUserData.fullName,
        hasAnonymousProfilePicture = itUserData.hasAnonymousProfilePicture,
        isPrivate = itUserData.isPrivate,
        isVerified = itUserData.isVerified,
        latestReelMedia = itUserData.latestReelMedia,
        profilePicUrl = itUserData.profilePicUrl,
        profilePicId = itUserData.profilePicId,
        username = itUserData.username
    )
}

fun ApiResponseUserFollow.toFollowersData(userId: Long) = users.map { itUserData ->
    FollowersData(
        uniqueType = itUserData.pk.toString().plus(userId.toString().take(6)).toLong(),
        analyzeUserId = userId,
        dsUserID = itUserData.pk,
        fullName = itUserData.fullName,
        hasAnonymousProfilePicture = itUserData.hasAnonymousProfilePicture,
        isPrivate = itUserData.isPrivate,
        isVerified = itUserData.isVerified,
        latestReelMedia = itUserData.latestReelMedia,
        profilePicUrl = itUserData.profilePicUrl,
        profilePicId = itUserData.profilePicId,
        username = itUserData.username
    )
}

fun ApiResponseUserFollow.toFollowingData(userId: Long) = users.map { itUserData ->
    FollowingData(
        uniqueType = itUserData.pk.toString().plus(userId.toString().take(6)).toLong(),
        analyzeUserId = userId,
        dsUserID = itUserData.pk,
        fullName = itUserData.fullName,
        hasAnonymousProfilePicture = itUserData.hasAnonymousProfilePicture,
        isPrivate = itUserData.isPrivate,
        isVerified = itUserData.isVerified,
        latestReelMedia = itUserData.latestReelMedia,
        profilePicUrl = itUserData.profilePicUrl,
        profilePicId = itUserData.profilePicId,
        username = itUserData.username
    )
}

fun List<FollowersData>.toOldFollowersData() = map {
    OldFollowersData(
        uniqueType = it.uniqueType,
        analyzeUserId = it.analyzeUserId,
        dsUserID = it.dsUserID,
        fullName = it.fullName,
        hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
        isPrivate = it.isPrivate,
        isVerified = it.isVerified,
        latestReelMedia = it.latestReelMedia,
        profilePicUrl = it.profilePicUrl,
        profilePicId = it.profilePicId,
        username = it.username
    )

}

fun List<FollowingData>.toOldFollowingData() = map {
    OldFollowingData(
        uniqueType = it.uniqueType,
        analyzeUserId = it.analyzeUserId,
        dsUserID = it.dsUserID,
        fullName = it.fullName,
        hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
        isPrivate = it.isPrivate,
        isVerified = it.isVerified,
        latestReelMedia = it.latestReelMedia,
        profilePicUrl = it.profilePicUrl,
        profilePicId = it.profilePicId,
        username = it.username
    )
}

fun ApiResponseUserDetails.toAccountsInfoData() = AccountsInfoData(
    userName = user.username,
    profilePic = user.profilePicUrl,
    followers = user.followerCount.toLong(),
    following = user.followingCount.toLong(),
    posts = user.mediaCount.toLong(),
    userId = user.pk
)