package com.avalon.calizer.utils

import android.graphics.Point
import android.graphics.PointF
import java.util.*

object Utils {
    fun generateUUID(): String {

        return UUID.randomUUID().toString().replace("-", "")
    }




    fun getTimeDifference(time: Date): Boolean {
        val timeNow: Date = Date(System.currentTimeMillis())
        return if (time.time <= -1) {
            true
        } else {
            (((timeNow.time - time.time) / 1000) % 3600 / 60).toInt() >= 3000000
        }
    }

    fun <R, A, B, C> ifNotNull(a: A?, b: B?, c: C?, block: (A, B, C) -> R): R? =
        if (a != null && b != null && c != null) {
            block(a, b, c)
        } else null

    fun <R,A,B> ifTwoNotNull(a:A?,b:B?,block:(A,B)->R):R?=
        if (a!=null&&b!=null){
            block(a,b)
        }else null
    fun <R,A,B,C,D,E> ifFiveNotNull(a:A?,b:B?,c:C?,d:D?,e:E?,block:(A,B,C,D,E)->R):R?=
        if (a!=null&&b!=null&&c!=null&&d!=null&&e!=null){
            block(a,b,c,d,e)
        }else null




}
