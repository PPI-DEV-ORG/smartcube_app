package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEdgeServerDto(
    @SerializedName("edgeServerId")
    val edgeServerId: UInt,

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
    val id: UInt,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("vendor")
    val vendor: String? = "",
)


@Serializable
data class InvitationCodeDto(
    @SerializedName("invitation_code")
    val invitationCode: String,
)

@Serializable
data class JoinServerDto(
    @SerializedName("id")
    val id: UInt,

    @SerializedName("user_id")
    val userId: UInt,

    @SerializedName("edge_server_id")
    val edgeServerId: UInt,

    @SerializedName("role_id")
    val roleId: UInt,
)
