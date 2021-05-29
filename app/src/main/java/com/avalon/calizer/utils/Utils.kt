package com.avalon.calizer.utils

import java.util.*

object Utils {
    fun generateUUID(): String {

        return UUID.randomUUID().toString().replace("-", "")
    }


    fun getTimeDifference(time: Date): Boolean {
        val timeNow: Date = Date(System.currentTimeMillis())

        return if (time.time <= -1){
            true
        }else{
            (((timeNow.time - time.time) / 1000) % 3600 / 60).toInt() >= 300
        }


    }




}
