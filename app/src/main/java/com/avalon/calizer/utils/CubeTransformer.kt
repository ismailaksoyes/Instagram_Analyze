package com.avalon.calizer.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CubeTransformer:ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val deltaY = 0.5F
        page.pivotX = if (position < 0F) page.width.toFloat() else 0F
        page.pivotY = page.height * deltaY
        page.rotationY = 30F * position
    }
}