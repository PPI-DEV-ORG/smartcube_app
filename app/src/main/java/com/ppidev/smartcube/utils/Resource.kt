package com.ppidev.smartcube.utils

sealed class Resource<out SuccessData, out ErrorData> {
    data class Success<out SuccessData>(val message: String, val data: SuccessData) : Resource<SuccessData, Nothing>()
    data class Error<out ErrorData>(val statusCode: Int, val message: String, val data: ErrorData? = null) : Resource<Nothing, ErrorData>()
    data class Loading<out SuccessData, out ErrorData>(val data: SuccessData? = null) : Resource<SuccessData, ErrorData>()
}
