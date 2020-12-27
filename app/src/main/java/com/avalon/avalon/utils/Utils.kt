package com.avalon.avalon.utils

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

}
