package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
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
        drawLine(canvas, paint, poseData, leftOffset, topOffset, bitmap)
        drawPoint(canvas, paint, poseData, leftOffset, topOffset, bitmap)


    }

    private fun drawPoint(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        leftOffset: Float,
        topOffset: Float,
        bitmap: Bitmap
    ) {

        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        for (i in poseData.getData()) {
            i.apply {
                first?.let { first ->
                    second?.let { second ->
                        Log.d("ImagePosTest", "$first $second")
                        val left = first + leftOffset
                        val top = second + topOffset

                        canvas?.drawCircle(left, top, 7f, paint)


                    }
                }
            }


        }
    }

    private fun drawLine(
        canvas: Canvas?,
        paint: Paint,
        poseData: PoseData,
        leftOffset: Float,
        topOffset: Float,
        bitmap: Bitmap
    ) {
        paint.color = Color.WHITE
        paint.strokeWidth = 5f

        poseData.apply {
            leftKnee?.let { leftKnee ->
                leftAnkle?.let { leftAnkle ->
                    canvas?.drawLine(
                        leftKnee.first + leftOffset,
                        leftKnee.second + topOffset,
                        leftAnkle.first + leftOffset,
                        leftAnkle.second + topOffset, paint
                    )
                }

            }
            rightKnee?.let { rightKnee ->
                rightAnkle?.let { rightAnkle ->
                    canvas?.drawLine(
                        rightKnee.first + leftOffset,
                        rightKnee.second + topOffset,
                        rightAnkle.first + leftOffset,
                        rightAnkle.second + topOffset, paint
                    )
                }
            }
            leftHip?.let { leftHip ->
                leftKnee?.let { leftKnee ->
                    canvas?.drawLine(
                        leftHip.first + leftOffset,
                        leftHip.second + topOffset,
                        leftKnee.first + leftOffset,
                        leftKnee.second + topOffset, paint
                    )
                }
            }
            rightHip?.let { rightHip ->
                rightKnee?.let { rightKnee ->
                    canvas?.drawLine(
                        rightHip.first + leftOffset,
                        rightHip.second + topOffset,
                        rightKnee.first + leftOffset,
                        rightKnee.second + topOffset, paint
                    )
                }
            }
            leftShoulder?.let { leftShoulder ->
                leftHip?.let { leftHip ->
                    canvas?.drawLine(
                        leftShoulder.first + leftOffset,
                        leftShoulder.second + topOffset,
                        leftHip.first + leftOffset,
                        leftHip.second + topOffset, paint
                    )
                }
            }

            rightShoulder?.let { rightShoulder ->
                rightHip?.let { rightHip ->
                    canvas?.drawLine(
                        rightShoulder.first + leftOffset,
                        rightShoulder.second + topOffset,
                        rightHip.first + leftOffset,
                        rightHip.second + topOffset, paint
                    )
                }
            }
            leftShoulder?.let { leftShoulder ->
                rightShoulder?.let { rightShoulder ->
                    canvas?.drawLine(
                        leftShoulder.first + leftOffset,
                        leftShoulder.second + topOffset,
                        rightShoulder.first + leftOffset,
                        rightShoulder.second + topOffset, paint
                    )
                }
            }
            leftHip?.let { leftHip ->
                rightHip?.let { rightHip ->
                    canvas?.drawLine(
                        leftHip.first + leftOffset,
                        leftHip.second + topOffset,
                        rightHip.first + leftOffset,
                        rightHip.second + topOffset, paint
                    )
                }
            }
            leftShoulder?.let { leftShoulder ->
                leftElbow?.let { leftElbow ->
                    canvas?.drawLine(
                        leftShoulder.first + leftOffset,
                        leftShoulder.second + topOffset,
                        leftElbow.first + leftOffset,
                        leftElbow.second + topOffset, paint
                    )

                }
            }
            rightShoulder?.let { rightShoulder ->
                rightElbow?.let { rightElbow ->
                    canvas?.drawLine(
                        rightShoulder.first + leftOffset,
                        rightShoulder.second + topOffset,
                        rightElbow.first + leftOffset,
                        rightElbow.second + topOffset, paint
                    )
                }
            }
            leftElbow?.let { leftElbow ->
                leftWrist?.let { leftWrist ->
                    canvas?.drawLine(
                        leftElbow.first + leftOffset,
                        leftElbow.second + topOffset,
                        leftWrist.first + leftOffset,
                        leftWrist.second + topOffset, paint
                    )
                }
            }
            rightElbow?.let { rightElbow ->
                rightWrist?.let { rightWrist ->
                    canvas?.drawLine(
                        rightElbow.first + leftOffset,
                        rightElbow.second + topOffset,
                        rightWrist.first + leftOffset,
                        rightWrist.second + topOffset, paint
                    )
                }
            }

        }


    }


}