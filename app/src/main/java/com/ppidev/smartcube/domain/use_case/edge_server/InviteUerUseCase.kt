package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IInviteUserUseCase
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InviteUerUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IInviteUserUseCase {
    override fun invoke(edgeServerId: UInt): Flow<Resource<InvitationCodeDto?, Any>> =
        flow {
            emit(Resource.Loading())
            emit(joinUserGroupServer(edgeServerId))
        }

    private suspend fun joinUserGroupServer(
        edgeServerId: UInt
    ): Resource<InvitationCodeDto?, Any> {
        try {
            val response =
                edgeServerRepository.get().getInvitationCode(edgeServerId = edgeServerId)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response.message, response.data)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}