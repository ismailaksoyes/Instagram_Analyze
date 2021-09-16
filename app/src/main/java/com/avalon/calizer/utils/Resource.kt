package com.avalon.calizer.utils


sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)

}