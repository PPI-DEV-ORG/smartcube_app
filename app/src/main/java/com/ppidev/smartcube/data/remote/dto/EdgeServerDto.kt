package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEdgeServerDto(
    @SerializedName("mqtt_user")
    val mqttUser: String? = "",

    @SerializedName("mqtt_password")
    val mqttPassword: String? = "",

    @SerializedName("mqtt_pub_topic")
    val mqttPubTopic: String? = "",

    @SerializedName("mqtt_sub_topic")
    val mqttSubTopic: String? = "",

    @SerializedName("egde_server_access_token")
    val egdeServerAccessToken: String? = "",

)

@Serializable
data class EdgeServerItemDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = "",

    @SerializedName("vendor")
    val vendor: String? = "",
)
