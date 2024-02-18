package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IJoinUserUseCase
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinUserUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IJoinUserUseCase {
    override fun invoke(invitationCode: String): Flow<Resource<JoinServerDto?, Any>> =
        flow {
            emit(Resource.Loading())
            emit(joinUserGroupServer(invitationCode))
        }

    private suspend fun joinUserGroupServer(
        invitationCode: String,
    ): Resource<JoinServerDto?, Any> {
        try {
            val response =
                edgeServerRepository.get().joinInvitationCode(invitationCode = invitationCode)

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