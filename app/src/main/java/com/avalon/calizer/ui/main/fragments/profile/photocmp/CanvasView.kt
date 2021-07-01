package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.avalon.calizer.data.local.profile.photoanalyze.PoseData
import javax.inject.Inject
import kotlin.random.Random

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var _height:Float = 0f
    private var _width:Float = 0f
    private var _poseData:PoseData? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
         val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
        }

        val ww = width.toFloat()
        val hh = height.toFloat()
        Log.d("GetSize","$ww $hh ${context.display?.width} ${context.display?.height} ")

        _poseData?.let {
            setData(_width,_height,canvas,paint)
        }


    }
    fun setPoseData(poseData: PoseData?){
        _poseData = poseData
    }
    fun setWidthCanvas(width:Float){
        this._width = width
    }
    fun setHeightCanvas(height:Float){
        this._height = height
    }
    fun setData(left1: Float, top1: Float,canvas: Canvas?,paint:Paint) {
        canvas?.drawRect(left1, top1, left1 + 15, top1 + 15, paint)
        for (i in 0..30) {
            val left = Random.nextInt(0, 1).toFloat()
            val top = Random.nextInt(0, 1).toFloat()

        }
    }

}