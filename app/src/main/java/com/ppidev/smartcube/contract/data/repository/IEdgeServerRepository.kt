package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import com.ppidev.smartcube.utils.ResponseApp

interface IEdgeServerRepository {
    suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ): ResponseApp<CreateEdgeServerDto?>

    suspend fun listEdgeServer(): ResponseApp<List<EdgeServerItemDto>>

    suspend fun getInvitationCode(edgeServerId: UInt): ResponseApp<InvitationCodeDto?>

    suspend fun joinInvitationCode(invitationCode: String): ResponseApp<JoinServerDto?>
}