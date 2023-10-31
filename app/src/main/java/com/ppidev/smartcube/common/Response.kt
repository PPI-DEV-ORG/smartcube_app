package com.ppidev.smartcube.common

import com.ppidev.smartcube.data.remote.dto.FcmMessage

data class Response<T>(
    val status: Boolean,
    val statusCode: Int,
    val message: String,
    val data: T
)