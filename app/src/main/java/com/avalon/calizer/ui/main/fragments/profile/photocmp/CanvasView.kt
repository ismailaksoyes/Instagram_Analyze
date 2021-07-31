package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.scale
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import kotlin.math.roundToInt


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
                val newBitmap = scaleBitmap(imageBitmap, hh)
                drawData(poseData, canvas, paint, ww, hh, newBitmap.first, newBitmap.second)
            }
        }


    }

    fun scaleBitmap(imageBitmap: Bitmap, height: Float): Pair<Bitmap, Float> {
        var newWidth:Int = 0
        var pointRatio:Float = 0f

        try {
            val ratio: Float = imageBitmap.width / imageBitmap.height.toFloat()
             newWidth = Math.round(height * ratio)
             pointRatio = imageBitmap.width / newWidth.toFloat()

        }catch (e:ArithmeticException){

        }
        return Pair(imageBitmap.scale(newWidth, height.toInt()), pointRatio)
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
        bitmap: Bitmap,
        ratio: Float
    ) {
        try {
            val leftOffset = (ww / 2) - (bitmap.width / 2)
            val topOffset = (hh / 2) - (bitmap.height / 2)
            val offset = PointF(leftOffset, topOffset)
            canvas?.drawBitmap(bitmap, offset.x, offset.y, paint)
            lineCreate(canvas, paint, poseData, offset, ratio)
            drawPoint(canvas, paint, poseData, offset, ratio)
        }catch (e:ArithmeticException){

        }



    }

    private fun drawPoint(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        offset: PointF,
        ratio: Float
    ) {

        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        for (i in poseData.getData()) {
            try {
                i?.apply {
                    val left = (x / ratio) + offset.x
                    val top = (y / ratio) + offset.y
                    canvas?.drawCircle(left, top, 7f, paint)
                }
            }catch (e:ArithmeticException){

            }



        }
    }

    private fun lineCreate(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        offset: PointF,
        ratio: Float
    ) {
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        poseData.apply {
            drawPointLine(LineData(leftKnee, leftAnkle, paint, canvas, ratio, offset))
            drawPointLine(LineData(rightKnee, rightAnkle, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftHip, leftKnee, paint, canvas, ratio, offset))
            drawPointLine(LineData(rightHip, rightKnee, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftShoulder, leftHip, paint, canvas, ratio, offset))
            drawPointLine(LineData(rightShoulder, rightHip, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftShoulder, rightShoulder, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftHip, rightHip, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftShoulder, leftElbow, paint, canvas, ratio, offset))
            drawPointLine(LineData(rightShoulder, rightElbow, paint, canvas, ratio, offset))
            drawPointLine(LineData(leftElbow, leftWrist, paint, canvas, ratio, offset))
            drawPointLine(LineData(rightElbow, rightWrist, paint, canvas, ratio, offset))

        }


    }

    private fun drawPointLine(lineData: LineData) {
        val ratio = lineData.ratio
        val offset = lineData.offset
        val x1 = lineData.firstPoint?.x
        val y1 = lineData.firstPoint?.y
        val x2 = lineData.secondPoint?.x
        val y2 = lineData.secondPoint?.y

        if (x1 != null && y1 != null && x2 != null && y2 != null) {
            try {
                lineData.canvas?.drawLine(
                    x1 / ratio + offset.x,
                    y1 / ratio + offset.y,
                    x2 / ratio + offset.x,
                    y2 / ratio + offset.y,
                    lineData.paint
                )
            }catch (e:ArithmeticException){

            }

        }
    }

    data class LineData(
        val firstPoint: PointF?,
        val secondPoint: PointF?,
        val paint: Paint,
        val canvas: Canvas?,
        val ratio: Float,
        val offset: PointF
    )


}