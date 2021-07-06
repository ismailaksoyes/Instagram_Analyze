package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData


class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var _poseData: PoseData? = null
    private var _imageBit: Bitmap? = null

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        val ww = width.toFloat()
        val hh = height.toFloat()

        _poseData?.let { poseData ->
            _imageBit?.let { imageBitmap ->
                drawData(poseData, canvas, paint, ww, hh, imageBitmap)
            }
        }


    }

    fun setPoseData(poseData: PoseData?, bitmap: Bitmap?) {
        _poseData = poseData
        _imageBit = bitmap
    }

    private fun sortListPairMidItem(list: List<Pair<Float, Float>>) =
        list.sortedWith(compareBy({ it.second }, { it.first })).asReversed()[list.size / 2]


    private fun drawData(
        poseData: PoseData,
        canvas: Canvas?,
        paint: Paint,
        ww: Float,
        hh: Float,
        bitmap: Bitmap
    ) {
        val leftOffset = (ww / 2) - (bitmap.width / 2)
        val topOffset = (hh / 2) - (bitmap.height / 2)
        canvas?.drawBitmap(bitmap, leftOffset, topOffset, paint)
        drawLine(canvas, paint, poseData, leftOffset, topOffset)
        drawPoint(canvas, paint, poseData, leftOffset, topOffset)


    }

    private fun drawPoint(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        leftOffset: Float,
        topOffset: Float
    ) {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        for (i in poseData.getData()) {
            val left = i.first + leftOffset
            val top = i.second + topOffset
            canvas?.drawCircle(left, top, 7f, paint)

        }
    }

    private fun drawLine(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        leftOffset: Float,
        topOffset: Float
    ) {
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        canvas?.drawLine(
            poseData.leftKnee.first + leftOffset,
            poseData.leftKnee.second + topOffset,
            poseData.leftAnkle.first + leftOffset,
            poseData.leftAnkle.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.rightKnee.first + leftOffset,
            poseData.rightKnee.second + topOffset,
            poseData.rightAnkle.first + leftOffset,
            poseData.rightAnkle.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftHip.first + leftOffset,
            poseData.leftHip.second + topOffset,
            poseData.leftKnee.first + leftOffset,
            poseData.leftKnee.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.rightHip.first + leftOffset,
            poseData.rightHip.second + topOffset,
            poseData.rightKnee.first + leftOffset,
            poseData.rightKnee.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftShoulder.first + leftOffset,
            poseData.leftShoulder.second + topOffset,
            poseData.leftHip.first + leftOffset,
            poseData.leftHip.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.rightShoulder.first + leftOffset,
            poseData.rightShoulder.second + topOffset,
            poseData.rightHip.first + leftOffset,
            poseData.rightHip.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftShoulder.first + leftOffset,
            poseData.leftShoulder.second + topOffset,
            poseData.rightShoulder.first + leftOffset,
            poseData.rightShoulder.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftHip.first + leftOffset,
            poseData.leftHip.second + topOffset,
            poseData.rightHip.first + leftOffset,
            poseData.rightHip.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftShoulder.first + leftOffset,
            poseData.leftShoulder.second + topOffset,
            poseData.leftElbow.first + leftOffset,
            poseData.leftElbow.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.rightShoulder.first + leftOffset,
            poseData.rightShoulder.second + topOffset,
            poseData.rightElbow.first + leftOffset,
            poseData.rightElbow.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.leftElbow.first + leftOffset,
            poseData.leftElbow.second + topOffset,
            poseData.leftWrist.first + leftOffset,
            poseData.leftWrist.second + topOffset, paint
        )
        canvas?.drawLine(
            poseData.rightElbow.first + leftOffset,
            poseData.rightElbow.second + topOffset,
            poseData.rightWrist.first + leftOffset,
            poseData.rightWrist.second + topOffset, paint
        )
    }


}