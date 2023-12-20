package com.ppidev.smartcube.contract.domain.use_case.edge_server

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import kotlinx.coroutines.flow.Flow

interface IAddEdgeServerUseCase {
    operator fun invoke(
        name: String,
        vendor: String,
        description: String
    ): Flow<Resource<ResponseApp<CreateEdgeServerDto?>>>
}

interface IListEdgeServerUseCase {
    operator fun invoke(): Flow<Resource<ResponseApp<List<EdgeServerItemDto>>>>
}

interface IInviteUserUseCase {
    operator fun invoke(edgeServerId: UInt): Flow<Resource<ResponseApp<InvitationCodeDto?>>>
}

interface IJoinUserUseCase {
    operator fun invoke(invitationCode: String): Flow<Resource<ResponseApp<JoinServerDto?>>>
}