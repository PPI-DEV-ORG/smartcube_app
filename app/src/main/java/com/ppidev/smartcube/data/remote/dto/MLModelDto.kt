package com.ppidev.smartcube.data.remote.dto

import com.ppidev.smartcube.domain.model.MLModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MLModelDto(
    @SerialName("model_name")
    val name: String,

    @SerialName("version")
    val version: String,

    @SerialName("smcb_wrapper_version")
    val smcbWrapperVersion: String,
)


fun MLModelDto.toMLModel(): MLModel {
    return MLModel(
        name = name,
        version = version,
        smcbWrapperVersion = smcbWrapperVersion
    )
}