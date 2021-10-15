package com.avalon.calizer.utils


sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorCode: Int) : Resource<T>(null, errorCode)

}