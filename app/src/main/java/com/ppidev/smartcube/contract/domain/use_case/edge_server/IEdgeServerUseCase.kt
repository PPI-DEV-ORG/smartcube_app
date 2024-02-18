package com.ppidev.smartcube.contract.domain.use_case.edge_server

import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import kotlinx.coroutines.flow.Flow

interface IAddEdgeServerUseCase {
    operator fun invoke(
        name: String,
        vendor: String,
        description: String
    ): Flow<Resource<CreateEdgeServerDto?, Any>>
}

interface IListEdgeServerUseCase {
    operator fun invoke(): Flow<Resource<List<EdgeServerItemDto>, Any>>
}

interface IInviteUserUseCase {
    operator fun invoke(edgeServerId: UInt): Flow<Resource<InvitationCodeDto?, Any>>
}

interface IJoinUserUseCase {
    operator fun invoke(invitationCode: String): Flow<Resource<JoinServerDto?, Any>>
}