package com.avalon.avalon.utils

import java.util.*

object Utils {
    fun generateUUID():String {

        return UUID.randomUUID().toString().replace("-", "")
    }
    fun getFriendShipUrl(userId:String,maxId:String?=null):String{

        return if(maxId.isNullOrEmpty()){
            "https://i.instagram.com/api/v1/friendships/$userId/followers/"
        }else{
            "https://i.instagram.com/api/v1/friendships/$userId/followers/?max_id=$maxId&rank_token=19748713375_${generateUUID()}"
        }
    }

}
