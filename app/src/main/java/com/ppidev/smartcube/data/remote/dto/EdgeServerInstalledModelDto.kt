package com.ppidev.smartcube.data.remote.dto

import com.ppidev.smartcube.domain.model.MachineLearningModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Response from MQTT using SerialName
@Serializable
data class EdgeServerInstalledModelDto(
    @SerialName("model_name")
    val name: String,

    @SerialName("version")
    val version: String,

    @SerialName("smcb_wrapper_version")
    val smcbWrapperVersion: String,
)

fun EdgeServerInstalledModelDto.toMLModel(): MachineLearningModel {
    return MachineLearningModel(
        name = name,
        version = version,
        smcbWrapperVersion = smcbWrapperVersion
    )
}