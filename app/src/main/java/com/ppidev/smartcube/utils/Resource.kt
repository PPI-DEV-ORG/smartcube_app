package com.ppidev.smartcube.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null, val statusCode: Int? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(statusCode: Int, message: String, data: T? = null): Resource<T>(data, message, statusCode)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}