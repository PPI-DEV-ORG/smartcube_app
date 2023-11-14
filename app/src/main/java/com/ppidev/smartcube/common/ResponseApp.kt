package com.ppidev.smartcube.common

data class ResponseApp<T>(
    val status: Boolean,
    val statusCode: Int,
    val message: String,
    val data: T
)
