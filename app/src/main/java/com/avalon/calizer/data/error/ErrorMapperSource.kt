package com.avalon.calizer.data.error

interface ErrorMapperSource {
    fun getErrorString(errorId:Int):String
    val errorsMap:Map<Int,String>
}