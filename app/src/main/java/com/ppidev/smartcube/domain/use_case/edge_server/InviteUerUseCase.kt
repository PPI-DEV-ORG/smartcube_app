package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IInviteUserUseCase
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InviteUerUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IInviteUserUseCase {
    override fun invoke(edgeServerId: UInt): Flow<Resource<ResponseApp<InvitationCodeDto?>>> =
        flow {
            emit(Resource.Loading())
            emit(joinUserGroupServer(edgeServerId))
        }

    private suspend fun joinUserGroupServer(
        edgeServerId: UInt
    ): Resource<ResponseApp<InvitationCodeDto?>> {
        try {
            val response =
                edgeServerRepository.get().getInvitationCode(edgeServerId = edgeServerId)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}