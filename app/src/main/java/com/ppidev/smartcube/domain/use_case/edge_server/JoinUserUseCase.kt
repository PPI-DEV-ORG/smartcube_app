package com.ppidev.smartcube.domain.use_case.edge_server

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IJoinUserUseCase
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinUserUseCase @Inject constructor(
    private val edgeServerRepository: Lazy<IEdgeServerRepository>
) : IJoinUserUseCase {
    override fun invoke(invitationCode: String): Flow<Resource<ResponseApp<JoinServerDto?>>> =
        flow {
            emit(Resource.Loading())
            emit(joinUserGroupServer(invitationCode))
        }

    private suspend fun joinUserGroupServer(
        invitationCode: String,
    ): Resource<ResponseApp<JoinServerDto?>> {
        try {
            val response =
                edgeServerRepository.get().joinInvitationCode(invitationCode = invitationCode)

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