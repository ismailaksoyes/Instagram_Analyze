package com.avalon.calizer.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions

@Composable
 fun GlideImage(
    imageModel: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    GlideImage(
        imageModel = imageModel,
        modifier = modifier.fillMaxWidth(),
        alignment = alignment,
        contentScale = contentScale,
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        alpha = alpha
    )
}