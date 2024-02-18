package com.ppidev.smartcube.utils

data class ResponseApp<T>(
    val status: Boolean,
    val statusCode: Int,
    val message: String,
    val data: T
)
