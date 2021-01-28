package com.avalon.calizer.utils

import java.util.*

object Utils {
    fun generateUUID():String {

        return UUID.randomUUID().toString().replace("-", "")
    }
    fun getFriendShipUrl(maxId:String?=null):String{

        return if(maxId.isNullOrEmpty()){
            ""
        }else{
            "?max_id=$maxId&rank_token=19748713375_${generateUUID()}"
        }
    }
    fun getTimeDifference(time:Date):Int{
        val timeNow:Date = Date(System.currentTimeMillis())
        //get minutes
        return ((( timeNow.time - time.time)/1000) % 3600 / 60).toInt()
    }

    fun getTimeStatus(date:Long):Boolean{
        val timeDif = Utils.getTimeDifference(Date(date))
        return true
    }

}
